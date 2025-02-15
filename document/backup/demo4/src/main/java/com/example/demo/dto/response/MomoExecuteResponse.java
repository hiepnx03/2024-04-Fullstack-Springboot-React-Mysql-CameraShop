package com.example.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MomoExecuteResponse {
    private String orderId;
    private String amount;
    private String fullName;
    private String orderInfo;

    // Getters and Setters
}
