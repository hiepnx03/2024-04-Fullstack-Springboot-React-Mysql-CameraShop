package com.example.camerashop_be.controller;


import com.example.camerashop_be.dto.OrderDetailDTO;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.security.service.UserDetail;
import com.example.camerashop_be.service.IOrderDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderDetailsById(@PathVariable(name = "id", required = false) Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();

            List<OrderDetailDTO> orderResponses = orderDetailService.getAllByOrderIdAndUserId(id, userDetail.getId());
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get order detail successful!", orderResponses));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

        }
    }
}
