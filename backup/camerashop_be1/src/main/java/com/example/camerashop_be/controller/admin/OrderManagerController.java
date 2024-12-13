package com.example.camerashop_be.controller.admin;

import com.example.camerashop_be.dto.OrderDetailDTO;
import com.example.camerashop_be.dto.ShippingStatusStatistical;
import com.example.camerashop_be.dto.filter.OrderFilter;
import com.example.camerashop_be.dto.response.OrderResponse;
import com.example.camerashop_be.entity.Filter;
import com.example.camerashop_be.entity.QueryOperator;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.repository.specs.OrderSpecification;
import com.example.camerashop_be.service.IOrderDetailService;
import com.example.camerashop_be.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/order")
public class OrderManagerController {
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;

    @PostMapping("/update-status")
    public ResponseEntity<ResponseObject> getAll(@RequestBody OrderResponse orderResponse) {
        try {
            OrderResponse orderResult = orderService.updateShippingStatus(orderResponse.getId(), orderResponse.getShippingStatus());
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Update orders successfully!", orderResult));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size) {

        try {
            Page<OrderResponse> orderResponses = orderService.getAll(page, size);
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get orders successfully!", orderResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseObject> filter(@RequestBody OrderFilter orderFilter
    ) {
        try {
            OrderSpecification orderSpecification = new OrderSpecification();
            if (orderFilter.getCreatedDate() == null || orderFilter.getCreatedDate().size() < 1) {
            } else if (orderFilter.getCreatedDate().get(0) == null) {
                orderSpecification.add(new Filter("createdDate", QueryOperator.IN, orderFilter.getCreatedDate().get(1)));
            } else if (orderFilter.getCreatedDate().get(1) == null) {
                orderSpecification.add(new Filter("createdDate", QueryOperator.IN, orderFilter.getCreatedDate().get(0)));
            } else if (orderFilter.getCreatedDate().get(0) != null && orderFilter.getCreatedDate().get(1) != null) {
                orderSpecification.add(new Filter("createdDate", QueryOperator.IN, orderFilter.getCreatedDate()));
            }
            if (orderFilter.getAddress() != null) {
                orderSpecification.add(new Filter("address", QueryOperator.LIKE, orderFilter.getAddress()));
            }
            if (orderFilter.getPayment() != null) {
                orderSpecification.add(new Filter("payment", QueryOperator.EQUAL, orderFilter.getPayment()));
            }
            if (orderFilter.getShippingStatusId() != null) {
                orderSpecification.add(new Filter("shippingStatusId", QueryOperator.EQUAL, orderFilter.getShippingStatusId()));
            }
            Page<OrderResponse> orderResponsePage = orderService.filter(orderSpecification, orderFilter.getPage(), orderFilter.getSize());
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Filter product successfully!", orderResponsePage));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/total")
    public ResponseEntity<ResponseObject> getTotal() {
        try {
            Integer total = orderService.totalOrders();
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get total orders successfully!", total));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/total-in-day")
    public ResponseEntity<ResponseObject> getTotalInDay() {
        try {
            Integer total = orderService.totalOrdersInDay(new Date());
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get total orders successfully!", total));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<ResponseObject> getRevenue() {
        try {
            Integer total = orderService.getRevenue();
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get total orders successfully!", total));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/revenue-last-month")
    public ResponseEntity<ResponseObject> getRevenueLastMonth() {

        try {
            Integer total = orderService.getRevenueMonth(Calendar.getInstance().get(Calendar.MONTH) == 0 ? 12 : Calendar.getInstance().get(Calendar.MONTH));
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get total orders successfully!", total));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/revenue-current-month")
    public ResponseEntity<ResponseObject> getRevenueCurrentMonth() {

        try {
            Integer total = orderService.getRevenueMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get total orders successfully!", total));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/statistical-shipping-status")
    public ResponseEntity<ResponseObject> getStatisticalShippingStatus() {

        try {
            List<ShippingStatusStatistical> list = orderService.getStatisticalShippingStatus();
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get Statistical Shipping Status successfully!", list));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/{orderId}/order-detail")
    public ResponseEntity<ResponseObject> getOrderDetailsById(@PathVariable(name = "orderId", required = false) Long orderId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();

            List<OrderDetailDTO> orderResponses = orderDetailService.getAllByOrderId(orderId);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get order detail successful!", orderResponses));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

        }
    }

    public static boolean isNumber(String text) {
        return text.chars().allMatch(Character::isDigit);
    }

    private String convertWithoutUnderStoke(String str) {
        return str.split("_")[0];
    }
}
