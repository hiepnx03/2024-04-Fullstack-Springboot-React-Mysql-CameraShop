package com.example.demo.dto.response;

import lombok.Data;

@Data
public class ChangePasswordResponse {

    private String message;

    // Constructor
    public ChangePasswordResponse(String message) {
        this.message = message;
    }
}
