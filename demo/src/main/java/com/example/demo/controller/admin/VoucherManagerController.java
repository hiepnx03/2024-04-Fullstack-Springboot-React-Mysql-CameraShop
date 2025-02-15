package com.example.demo.controller.admin;

import com.example.demo.dto.VoucherDTO;
import com.example.demo.dto.request.ApplyVoucherRequest;
import com.example.demo.dto.response.ApplyVoucherResponse;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vouchers")
@RequiredArgsConstructor
@Slf4j
public class VoucherManagerController {

    private final VoucherService voucherService;

    @Operation(summary = "Add a new voucher", description = "Create a new voucher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher created successfully"),
            @ApiResponse(responseCode = "500", description = "Error creating voucher")
    })
    @PostMapping
    public ResponseEntity<ResponseObject> addVoucher(@RequestBody VoucherDTO voucherDTO) {
        try {
            VoucherDTO savedVoucher = voucherService.addVoucher(voucherDTO);
            log.info("Voucher created successfully with ID: {}", savedVoucher.getId());
            return ResponseEntity.ok(new ResponseObject("200", "Voucher created successfully", savedVoucher));
        } catch (Exception e) {
            log.error("Error creating voucher: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error creating voucher", null));
        }
    }

    @Operation(summary = "Update a voucher", description = "Update an existing voucher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher updated successfully"),
            @ApiResponse(responseCode = "404", description = "Voucher not found"),
            @ApiResponse(responseCode = "500", description = "Error updating voucher")
    })
    @PutMapping("/{voucherId}")
    public ResponseEntity<ResponseObject> updateVoucher(@PathVariable Long voucherId, @RequestBody VoucherDTO voucherDTO) {
        try {
            VoucherDTO updatedVoucher = voucherService.updateVoucher(voucherId, voucherDTO);
            if (updatedVoucher == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Voucher not found", null));
            }
            log.info("Voucher updated successfully with ID: {}", voucherId);
            return ResponseEntity.ok(new ResponseObject("200", "Voucher updated successfully", updatedVoucher));
        } catch (Exception e) {
            log.error("Error updating voucher with ID {}: {}", voucherId, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error updating voucher", null));
        }
    }

    @Operation(summary = "Get all vouchers", description = "Retrieve a list of all vouchers")
    @ApiResponse(responseCode = "200", description = "Vouchers retrieved successfully")
    @GetMapping
    public ResponseEntity<ResponseObject> getAllVouchers() {
        try {
            List<VoucherDTO> vouchers = voucherService.getAllVouchers();
            log.info("Successfully retrieved {} vouchers", vouchers.size());
            return ResponseEntity.ok(new ResponseObject("200", "Vouchers retrieved successfully", vouchers));
        } catch (Exception e) {
            log.error("Error retrieving vouchers: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving vouchers", null));
        }
    }

    @Operation(summary = "Get voucher by ID", description = "Retrieve a voucher by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Voucher not found"),
            @ApiResponse(responseCode = "500", description = "Error retrieving voucher")
    })
    @GetMapping("/{voucherId}")
    public ResponseEntity<ResponseObject> getVoucherById(@PathVariable Long voucherId) {
        try {
            VoucherDTO voucher = voucherService.getVoucherById(voucherId);
            if (voucher == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Voucher not found", null));
            }
            log.info("Voucher retrieved successfully with ID: {}", voucherId);
            return ResponseEntity.ok(new ResponseObject("200", "Voucher retrieved successfully", voucher));
        } catch (Exception e) {
            log.error("Error retrieving voucher with ID {}: {}", voucherId, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving voucher", null));
        }
    }

    @Operation(summary = "Find voucher by code", description = "Retrieve a voucher by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Voucher not found"),
            @ApiResponse(responseCode = "500", description = "Error retrieving voucher")
    })
    @GetMapping("/search")
    public ResponseEntity<ResponseObject> findVoucherByCode(@RequestParam String code) {
        try {
            VoucherDTO voucher = voucherService.findVoucherByCode(code);
            if (voucher == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Voucher not found", null));
            }
            log.info("Voucher retrieved successfully with code: {}", code);
            return ResponseEntity.ok(new ResponseObject("200", "Voucher retrieved successfully", voucher));
        } catch (Exception e) {
            log.error("Error retrieving voucher with code {}: {}", code, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving voucher", null));
        }
    }

    @Operation(summary = "Delete a voucher", description = "Delete a voucher by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Error deleting voucher")
    })
    @DeleteMapping("/{voucherId}")
    public ResponseEntity<ResponseObject> deleteVoucher(@PathVariable Long voucherId) {
        try {
            voucherService.deleteVoucher(voucherId);
            log.info("Voucher deleted successfully with ID: {}", voucherId);
            return ResponseEntity.ok(new ResponseObject("200", "Voucher deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting voucher with ID {}: {}", voucherId, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error deleting voucher", null));
        }
    }

    @Operation(summary = "Apply a voucher", description = "Apply a voucher to a request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher applied successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid voucher"),
            @ApiResponse(responseCode = "500", description = "Error applying voucher")
    })
    @PostMapping("/apply")
    public ResponseEntity<ResponseObject> applyVoucher(@RequestBody ApplyVoucherRequest request) {
        try {
            ApplyVoucherResponse response = voucherService.applyVoucher(request);
            if (response.getSuccess()) {
                log.info("Voucher applied successfully: {}", response.getMessage());
                return ResponseEntity.ok(new ResponseObject("200", response.getMessage(), response));
            } else {
                log.warn("Failed to apply voucher: {}", response.getMessage());
                return ResponseEntity.status(400).body(new ResponseObject("400", response.getMessage(), response));
            }
        } catch (Exception e) {
            log.error("Error applying voucher: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Có lỗi xảy ra khi áp dụng voucher", null));
        }
    }
}
