package com.example.demo.controller.admin;


import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.ShippingStatus;
import com.example.demo.service.ShippingStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/admin/shipping-status")
@AllArgsConstructor
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
