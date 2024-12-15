package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "password_history")
@Data
public class PasswordHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Liên kết với người dùng

    @Column(name = "password", nullable = false)
    private String password; // Mật khẩu cũ (được mã hóa)

    @Column(name = "change_time", nullable = false)
    private Date changeTime; // Thời gian thay đổi mật khẩu
}
