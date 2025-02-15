package com.example.demo.controller.admin;

import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.ShippingStatus;
import com.example.demo.service.ShippingStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/shipping-status")
@AllArgsConstructor
@Slf4j
public class ShippingStatusController {
    private final ShippingStatusService shippingStatusService;

    @Operation(summary = "Get all shipping statuses", description = "Retrieve a list of all shipping statuses")
    @ApiResponse(responseCode = "200", description = "Shipping statuses retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Failed to retrieve shipping statuses")
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAll() {
        try {
            List<ShippingStatus> shippingStatuses = shippingStatusService.getAll();
            log.info("Successfully retrieved {} shipping statuses", shippingStatuses.size());
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get shipping status successfully!", shippingStatuses));
        } catch (Exception e) {
            log.error("Error retrieving shipping statuses: {}", e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", "Get failed!", ""));
        }
    }
}
