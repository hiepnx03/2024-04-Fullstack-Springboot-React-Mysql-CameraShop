package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private Set<Long> roles = new HashSet<>(); // Khởi tạo Set để tránh null

    // Constructors, getters, and setters
}
