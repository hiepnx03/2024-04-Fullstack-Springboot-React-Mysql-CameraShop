package com.example.camerashop_be.controller;

import com.example.camerashop_be.dto.DeliveryDTO;
import com.example.camerashop_be.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryDTO> createDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        return ResponseEntity.ok(deliveryService.createDelivery(deliveryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable int id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> updateDelivery(@PathVariable int id, @RequestBody DeliveryDTO deliveryDTO) {
        return ResponseEntity.ok(deliveryService.updateDelivery(id, deliveryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable int id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
