package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZaloPayCreatePaymentResponse {
    private String returnCode;
    private String returnMessage;
    private String orderUrl;  // URL thanh toán ZaloPay

    // Getters and Setters
}
