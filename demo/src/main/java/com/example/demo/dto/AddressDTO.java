package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddressDTO {
    private Long id;

    @NotBlank(message = "First name cannot be blank.")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters.")
    private String lastName;

    @NotBlank(message = "Phone number cannot be blank.")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be a valid format, including a country code.")
    private String phone;

    @NotNull(message = "Default address status cannot be null.")
    private Integer isDefault;

    @NotBlank(message = "City cannot be blank.")
    private String city;

    @NotBlank(message = "District cannot be blank.")
    private String district;

    @NotBlank(message = "Ward cannot be blank.")
    private String ward;

    @NotBlank(message = "Street cannot be blank.")
    private String street;

    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    private String description;

    @NotNull(message = "Status cannot be null.")
    private Integer status;

    @NotNull(message = "User ID cannot be null.")
    private Long userId;
}
