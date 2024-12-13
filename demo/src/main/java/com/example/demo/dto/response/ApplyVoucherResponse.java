package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyVoucherResponse {
    private Boolean success;       // Kết quả có thành công hay không
    private String message;        // Thông điệp cho người dùng
    private Long discountAmount;   // Số tiền giảm sau khi áp dụng voucher (nếu có)
    private Long finalAmount;      // Số tiền cuối cùng sau khi giảm giá (nếu có)
}