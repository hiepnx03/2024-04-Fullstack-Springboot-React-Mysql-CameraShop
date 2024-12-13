package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private Integer isDefault;
    private String city;
    private String district;
    private String ward;
    private String street;
    private String description;
    private Integer status;

    private Long userId;
}
