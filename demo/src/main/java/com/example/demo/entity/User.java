package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends Base<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;


    private String phone;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    private Boolean enabled;
    @Column(name = "failed_attempt")
    private Integer failedAttempt;
    @Column(name = "lock_time")
    private Date lockTime;
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
    private List<Role> roles   = new ArrayList<>(); // Khởi tạo để tránh NullPointerException
}