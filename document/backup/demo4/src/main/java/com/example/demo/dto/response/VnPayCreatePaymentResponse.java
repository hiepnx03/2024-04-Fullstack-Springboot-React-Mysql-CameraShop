package com.example.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VnPayCreatePaymentResponse {
    private String message;
    private String paymentUrl;

    // Getter, Setter, Constructor
}
