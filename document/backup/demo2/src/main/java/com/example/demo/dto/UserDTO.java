package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String email;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    private String phone;
    private Integer status;
    private Instant expiryDate;
    private String refreshToken;
    private List<String> roles; // Khởi tạo Set để tránh null
    // Constructors, getters, and setters
}
