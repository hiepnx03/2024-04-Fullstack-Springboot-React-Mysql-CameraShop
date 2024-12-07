package com.example.demo.entity;

import com.example.demo.repository.RoleRepository;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Boolean enabled;
    @Column(columnDefinition = "integer default 1")
    private Integer status;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "refresh_token", length = 500, unique = true)
    private String refreshToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles   = new ArrayList<>(); // Khởi tạo để tránh NullPointerException


}