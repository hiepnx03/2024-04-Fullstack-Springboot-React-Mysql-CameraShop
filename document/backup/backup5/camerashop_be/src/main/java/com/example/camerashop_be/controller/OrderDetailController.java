package com.example.camerashop_be.controller;

import com.example.camerashop_be.dto.OrderDetailDTO;
import com.example.camerashop_be.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable long id) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(@PathVariable long id, @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, orderDetailDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}
