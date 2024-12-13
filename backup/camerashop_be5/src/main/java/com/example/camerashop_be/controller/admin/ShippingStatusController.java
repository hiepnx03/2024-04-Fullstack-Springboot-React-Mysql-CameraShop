package com.example.camerashop_be.controller.admin;

import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.entity.ShippingStatus;
import com.example.camerashop_be.service.impl.ShippingStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/shipping-status")
public class ShippingStatusController {
    private final ShippingStatusService shippingStatusService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAll() {
        try {
            List<ShippingStatus> shippingStatuses = shippingStatusService.getAll();
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get shipping status successfully!", shippingStatuses));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", "Get failed!", ""));
        }
    }
}
