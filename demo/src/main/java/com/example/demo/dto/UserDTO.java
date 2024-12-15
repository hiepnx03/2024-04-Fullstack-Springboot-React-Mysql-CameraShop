package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String userName;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters.")
    private String password;

    @NotBlank(message = "First name cannot be blank.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;

    @NotBlank(message = "Verification code cannot be blank.")
    private String verificationCode;

    @NotNull(message = "Enabled status cannot be null.")
    private Boolean enabled;

    @NotNull(message = "Failed attempt count cannot be null.")
    private Integer failedAttempt;

    @NotNull(message = "Lock time cannot be null.")
    private Date lockTime;

    private Date lastLoginTime;

    private Date lastFailedAttemptTime;

    private String token;

    private Instant expiryDate;

    @NotNull(message = "Status cannot be null.")
    private Integer status;

    private String refreshToken;

    @NotEmpty(message = "Roles cannot be empty.")
    private List<String> roles; // Ensure that the roles list is not empty
}
