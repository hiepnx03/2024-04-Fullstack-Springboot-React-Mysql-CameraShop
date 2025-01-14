package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api")
@RestController
public class MoMoCallbackController {

    @PostMapping("/momo/callback")
    public void handleMoMoCallback(@RequestBody Map<String, String> response) {
        // Xử lý phản hồi từ MoMo (kiểm tra signature, trạng thái giao dịch, etc.)
        String orderId = response.get("orderId");
        String status = response.get("status");
        String signature = response.get("signature");

        // Kiểm tra chữ ký và xác thực trạng thái giao dịch

        if ("SUCCESS".equals(status)) {
            // Cập nhật trạng thái đơn hàng trong hệ thống
        } else {
            // Xử lý lỗi giao dịch
        }
    }
}
