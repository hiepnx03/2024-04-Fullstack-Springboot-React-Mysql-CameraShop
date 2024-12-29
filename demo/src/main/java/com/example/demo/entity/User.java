package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends Base<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Username is required")
    @Column(nullable = false, unique = true, length = 50)
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "First name is required")
    @Column(nullable = false, length = 50,columnDefinition = "NVARCHAR(50)")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false, length = 50,columnDefinition = "NVARCHAR(50)")
    private String lastName;

    private String phone;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private Boolean enabled;

    @Column(name = "failed_attempt")
    private Integer failedAttempt = 0;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime; // Trường này lưu trữ thời gian đăng nhập cuối cùng

    @Column(name = "last_failed_attempt_time")
    private Date lastFailedAttemptTime; // Trường này lưu trữ thời gian đăng nhập sai cuối cùng

    private String token;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(columnDefinition = "integer default 1")
    private Integer status;

    @Column(name = "refresh_token", length = 500, unique = true)
    private String refreshToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>(); // Khởi tạo để tránh NullPointerException
}
