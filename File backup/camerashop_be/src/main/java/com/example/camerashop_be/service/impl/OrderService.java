package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.converter.AddressConverter;
import com.example.camerashop_be.converter.OrderConverter;
import com.example.camerashop_be.converter.OrderDetailConverter;
import com.example.camerashop_be.dto.ShippingStatusStatistical;
import com.example.camerashop_be.dto.request.OrderRequest;
import com.example.camerashop_be.dto.response.OrderResponse;
import com.example.camerashop_be.entity.*;
import com.example.camerashop_be.repository.*;
import com.example.camerashop_be.repository.specs.OrderSpecification;
import com.example.camerashop_be.service.IOrderService;
import com.example.camerashop_be.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import jakarta.persistence.EntityNotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final AddressRepo addressRepo;
    private final OrderDetailRepo orderDetailRepo;
    private final AddressConverter addressConverter;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final IProductService productService;
    private final OrderDetailConverter orderDetailConverter;
    private final OrderConverter orderConverter;
    @Value("${server.frontend}")
    private String urlFrontend;
    private final ShippingStatusService shippingStatusService;
    private JavaMailSender mailSender;
    private final String contentUnVerified = "FRUIT SHOP has received your order and is processing it. You will receive a follow-up notification when your order is ready to be shipped.";
    private final String contentVerified = "FRUIT SHOP has processed your order and is forwarding it to shipping. You will receive a follow-up notification when your order is ready to be shipped.";
    private final String contentDelivering = "FRUIT SHOP has handed over your order to the carrier. You will receive a follow-up notification when your order is ready to be shipped.";
    private final String contentDelivered = "Your order has been successfully delivered.";
    private final String contentCanceling = "Your order cancellation request is being processed. The store staff will call you to confirm the request as soon as possible.";
    private final String contentCancel = "Your order has been cancelled.";

    @Transactional
    @Override
    public Boolean addOrder(OrderRequest orderRequest) {
        try {

            ShippingStatus shippingStatus = shippingStatusService.getByName(EShippingStatus.UNVERIFIED.getName());
            shippingStatus.setName(EShippingStatus.UNVERIFIED.getName());
            User user = userRepo.findById(orderRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User " + orderRequest.getUserId() + " does not exist!"));
            if (orderRequest.getOrderDetails().stream()
                    .anyMatch(item -> productService.getById(item.getProductId()).getQuantity() <= 0
                            || productService.getById(item.getProductId()).getQuantity() < item.getQuantity()
                            || productService.getById(item.getProductId()).getStatus() == 0)) {
                throw new RuntimeException("Quantity is not enough");
            }
            Payment p = orderRequest.getPayment();
            Order order = new Order();
            order.setPhone(orderRequest.getPhone());
            order.setFullName(orderRequest.getFullName());
            order.setStatus(EStatus.ACTIVE.getName());
            order.setShippingStatus(shippingStatus);
            order.setShippingCost(orderRequest.getShippingCost());
            order.setUser(user);
            order.setAddress(orderRequest.getAddress());
            order.setDescription(orderRequest.getDescription());
            long total = 0L;
            List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(dto -> orderDetailConverter.convertToEntity(dto)).collect(Collectors.toList());
            orderDetails.forEach(e -> e.setBill(order));
            for (OrderDetail orderDetail : orderDetails) {
                total += (orderDetail.getPrice() - orderDetail.getPrice() * orderDetail.getDiscount() / 100) * orderDetail.getQuantity();
            }
            if (total > 250000) {
                order.setTotal(total);
            } else {
                order.setTotal(total + 30000);
            }
            order.setOrderDetails(orderDetails);
            p.setBill(order);
            order.setPayment(p);
            orderRepo.save(order);

            List<Product> products = orderDetails.stream().map(e -> {
                Product pro = productService.getProductById(e.getProduct().getId());
                pro.setQuantity(pro.getQuantity() - e.getQuantity());
                return pro;
            }).collect(Collectors.toList());
            productService.add(products);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Page<OrderResponse> getByUserId(Long userId, Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
            Page<Order> orderResponses = orderRepo.findAllByUserIdAndStatus(userId, EStatus.ACTIVE.getName(), pageable);
            return orderConverter.convertToResponse(orderResponses);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public OrderResponse getById(Long id) {
        return orderConverter.convertToResponse(orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " does not exist!")));
    }

    @Override
    public OrderResponse updateStatusShipping(Long id, String status) {
        ShippingStatus shippingStatus = shippingStatusService.getByName(status);
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " does not exist!"));
        order.setShippingStatus(shippingStatus);
        return orderConverter.convertToResponse(orderRepo.save(order));
    }

    @Override
    public Page<OrderResponse> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return orderConverter.convertToResponse(orderRepo.findAll(pageable));
    }

    @Override
    public Page<OrderResponse> filter(OrderSpecification orderSpecification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return orderConverter.convertToResponse(orderRepo.findAll(orderSpecification, pageable));
    }

    @Override
    public OrderResponse updateShippingStatus(Long id, ShippingStatus shippingStatus) {
        Order bill = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " does not exist!"));
        bill.setShippingStatus(shippingStatus);
        Order billSaved = orderRepo.save(bill);
        this.sendEmailUpdateStatus(billSaved, urlFrontend);
        return orderConverter.convertToResponse(billSaved);
    }

    @Override
    public Integer totalOrders() {
        return orderRepo.totalOrders();
    }

    @Override
    public Integer totalOrdersInDay(Date date) {
        return orderRepo.totalOrdersInDay(date);
    }

    @Override
    public Integer getRevenue() {
        return orderRepo.totalRevenue();
    }

    @Override
    public Integer getRevenueMonth(Integer i) {
        return orderRepo.totalRevenueMonth(i);
    }

    @Override
    public List<ShippingStatusStatistical> getStatisticalShippingStatus() {
        return orderRepo.statisticalShippingStatus();
    }

    private void sendEmailUpdateStatus(Order bill, String siteURL) {
        try {
            String toAddress = bill.getUser().getEmail();
            String fromAddress = "tuyencpu@gmail.com";
            String senderName = "FRUIT SHOP";
            String subject = "FRUIT SHOP informs you of the status of your order #" + bill.getId();
            String content = "Dear [[name]],<br>" +
                    "[[contentStatus]]"
                    + "<br><br>You can click the link below to to check your order status:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">" +
                    "" +
                    "<img src=\"https://ci3.googleusercontent.com/proxy/9jgAlmsBdeW4k7SN4PYFou5sXZJDwnQpE7L9_GrSxd5p43TiK3Li0WMJzOQe5MceZB1LO4lMeQJZmrwOL93w66V3ag62T0K_S3QylDt6fmkwodqj=s0-d-e1-ft#https://img.alicdn.com/tfs/TB1qEWqJbr1gK0jSZR0XXbP8XXa-300-50.jpg\" style=\"max-width:300px\" border=\"0\" class=\"CToWUd\" data-bit=\"iit\"></a></h3>"
                    + "<br>Address: " + bill.getAddress() + "<br>"
                    + "<br>Thank you,<br>"
                    + "FRUIT SHOP.";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", bill.getUser().getLastName() + " " + bill.getUser().getFirstName());
            String verifyURL = siteURL + "/account/order/" + bill.getId();

            content = content.replace("[[URL]]", verifyURL);


            if (bill.getShippingStatus().getName().equals(EShippingStatus.UNVERIFIED.getName())) {
                content = content.replace("[[contentStatus]]", contentUnVerified);
            } else if (bill.getShippingStatus().getName().equals(EShippingStatus.VERIFIED.getName())) {
                content = content.replace("[[contentStatus]]", contentVerified);
            } else if (bill.getShippingStatus().getName().equals(EShippingStatus.DELIVERING.getName())) {
                content = content.replace("[[contentStatus]]", contentDelivering);
            } else if (bill.getShippingStatus().getName().equals(EShippingStatus.DELIVERED.getName())) {
                content = content.replace("[[contentStatus]]", contentDelivered);
            } else if (bill.getShippingStatus().getName().equals(EShippingStatus.CANCELED.getName())) {
                content = content.replace("[[contentStatus]]", contentCancel);
            } else if (bill.getShippingStatus().getName().equals(EShippingStatus.CANCELING.getName())) {
                content = content.replace("[[contentStatus]]", contentCanceling);
            }
            helper.setText(content, true);
            mailSender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

    }

}
