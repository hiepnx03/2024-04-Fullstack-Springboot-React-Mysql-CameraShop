package com.example.demo.controller.admin;

import com.example.demo.dto.VoucherDTO;
import com.example.demo.dto.request.ApplyVoucherRequest;
import com.example.demo.dto.response.ApplyVoucherResponse;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vouchers")
@RequiredArgsConstructor
public class VoucherManagerController {

    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<ResponseObject> addVoucher(@RequestBody VoucherDTO voucherDTO) {
        try {
            VoucherDTO savedVoucher = voucherService.addVoucher(voucherDTO);
            return ResponseEntity.ok(new ResponseObject("200", "Voucher created successfully", savedVoucher));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error creating voucher", null));
        }
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<ResponseObject> updateVoucher(@PathVariable Long voucherId, @RequestBody VoucherDTO voucherDTO) {
        try {
            VoucherDTO updatedVoucher = voucherService.updateVoucher(voucherId, voucherDTO);
            if (updatedVoucher == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Voucher not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Voucher updated successfully", updatedVoucher));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error updating voucher", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllVouchers() {
        try {
            List<VoucherDTO> vouchers = voucherService.getAllVouchers();
            return ResponseEntity.ok(new ResponseObject("200", "Vouchers retrieved successfully", vouchers));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving vouchers", null));
        }
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<ResponseObject> getVoucherById(@PathVariable Long voucherId) {
        try {
            VoucherDTO voucher = voucherService.getVoucherById(voucherId);
            if (voucher == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Voucher not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Voucher retrieved successfully", voucher));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving voucher", null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> findVoucherByCode(@RequestParam String code) {
        try {
            VoucherDTO voucher = voucherService.findVoucherByCode(code);
            if (voucher == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Voucher not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Voucher retrieved successfully", voucher));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving voucher", null));
        }
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<ResponseObject> deleteVoucher(@PathVariable Long voucherId) {
        try {
            voucherService.deleteVoucher(voucherId);
            return ResponseEntity.ok(new ResponseObject("200", "Voucher deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error deleting voucher", null));
        }
    }


    @PostMapping("/apply")
    public ResponseEntity<ResponseObject> applyVoucher(@RequestBody ApplyVoucherRequest request) {
        try {
            ApplyVoucherResponse response = voucherService.applyVoucher(request);
            if (response.getSuccess()) {
                return ResponseEntity.ok(new ResponseObject("200", response.getMessage(), response));
            } else {
                return ResponseEntity.status(400).body(new ResponseObject("400", response.getMessage(), response));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Có lỗi xảy ra khi áp dụng voucher", null));
        }
    }
}
