package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Data;

import java.util.Date;


@Data
public class PasswordHistoryDTO {
    private Long id;
    private User user; // Liên kết với người dùng
    private String password; // Mật khẩu cũ (được mã hóa)
    private Date changeTime; // Thời gian thay đổi mật khẩu
}
