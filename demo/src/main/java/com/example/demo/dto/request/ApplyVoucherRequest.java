package com.example.demo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyVoucherRequest {
    private String voucherCode;  // Mã voucher
    private Long userId;         // ID của người dùng
    private Long orderTotal;     // Tổng giá trị đơn hàng (có thể tính toán trong backend)
}