package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String orderId;
    private long amount; // Amount in VND (in cents, so 10000 is 100.00 VND)
    private String orderDescription;
    private String bankCode; // Optional
    private String language; // Optional
    private String ipAddress;
}
