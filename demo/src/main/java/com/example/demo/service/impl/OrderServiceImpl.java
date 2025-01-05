package com.example.demo.service.impl;


import com.example.demo.converter.OrderDetailConverter;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ShippingStatusStatistical;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShippingStatusRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShippingStatusService;
import com.example.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShippingStatusRepository shippingStatusRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ShippingStatusService shippingStatusService;
    private final ProductService productService;
    private final OrderDetailConverter orderDetailConverter;

    @Override
    public Boolean addOrder(OrderRequest orderRequest) {
        try {

            ShippingStatus shippingStatus = shippingStatusService.getByName(EShippingStatus.UNVERIFIED.getName());
            shippingStatus.setName(EShippingStatus.UNVERIFIED.getName());
            User user = userRepository.findById(orderRequest.getUserId())
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
            List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(orderDetailConverter::convertToEntity).collect(Collectors.toList());
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
            orderRepository.save(order);

            List<Product> products =orderDetails.stream().map(e->{
                Product pro= productService.getProductById(e.getProduct().getId());
                pro.setQuantity(pro.getQuantity()-e.getQuantity());
                return pro;
            }).collect(Collectors.toList());
            productService.addProduct((ProductDTO) products);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Page<OrderResponse> getByUserId(Long userId, Integer page, Integer size) {
        return null;
    }

    @Override
    public OrderResponse getById(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateStatusShipping(Long id, String status) {
        return null;
    }

    @Override
    public Page<OrderResponse> getAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public OrderResponse updateShippingStatus(Long id, ShippingStatus shippingStatus) {
        return null;
    }

    @Override
    public Integer totalOrders() {
        return 0;
    }

    @Override
    public Integer totalOrdersInDay(Date date) {
        return 0;
    }

    @Override
    public Integer getRevenue() {
        return 0;
    }

    @Override
    public Integer getRevenueMonth(Integer i) {
        return 0;
    }

    @Override
    public List<ShippingStatusStatistical> getStatisticalShippingStatus() {
        return List.of();
    }
}