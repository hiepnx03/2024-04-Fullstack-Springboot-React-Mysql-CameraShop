package com.example.camerashop_be.controller;

import com.example.camerashop_be.dto.PaymentDTO;
import com.example.camerashop_be.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.createPayment(paymentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable int id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable int id, @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.updatePayment(id, paymentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
