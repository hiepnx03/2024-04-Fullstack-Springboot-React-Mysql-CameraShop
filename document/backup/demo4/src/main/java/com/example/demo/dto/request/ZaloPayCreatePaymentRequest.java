package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZaloPayCreatePaymentRequest {
    private String orderId;
    private long amount;
    private String orderInfo;
    private String returnUrl;
    private String notifyUrl;

    // Getters and Setters
}
