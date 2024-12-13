package com.example.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MomoOption {
    private String momoApiUrl;
    private String secretKey;
    private String accessKey;
    private String returnUrl;
    private String notifyUrl;
    private String partnerCode;
    private String requestType;

    // Getters and Setters
}
