package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long amount; // Số tiền
    private String bankCode; // Mã ngân hàng (optional)
    private String ipAddress; // Địa chỉ IP của người dùng
    private String orderDescription; // Mô tả đơn hàng
    private String orderType; // Mã danh mục hàng hóa
    private String returnUrl; // URL thông báo kết quả
}
