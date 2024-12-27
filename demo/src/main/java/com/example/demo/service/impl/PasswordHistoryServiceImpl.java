package com.example.demo.service.impl;

import com.example.demo.entity.PasswordHistory;
import com.example.demo.entity.User;
import com.example.demo.repository.PasswordHistoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PasswordHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PasswordHistoryServiceImpl implements PasswordHistoryService {

    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    // Thay đổi mật khẩu
    @Override
    public String changePassword(Long userId, String oldPassword, String newPassword) {
        // Kiểm tra người dùng tồn tại
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra mật khẩu cũ có chính xác không
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "Old password is incorrect";
        }

        // Kiểm tra mật khẩu mới có trùng với mật khẩu cũ không (trong lịch sử mật khẩu)
        if (isPasswordUsedBefore(user, newPassword)) {
            return "New password cannot be the same as old password";
        }

        // Lưu mật khẩu cũ vào PasswordHistory
        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUser(user);
        passwordHistory.setPassword(user.getPassword()); // Lưu mật khẩu cũ
        passwordHistory.setChangeTime(new Date()); // Thời gian thay đổi mật khẩu
        passwordHistoryRepository.save(passwordHistory);

        // Cập nhật mật khẩu mới cho người dùng
        user.setPassword(passwordEncoder.encode(newPassword)); // Mã hóa mật khẩu mới
        userRepository.save(user);

        return "Password changed successfully";
    }

    // Kiểm tra mật khẩu có bị sử dụng trong lịch sử không
    private boolean isPasswordUsedBefore(User user, String newPassword) {
        List<PasswordHistory> passwordHistories = passwordHistoryRepository.findByUser(user);
        for (PasswordHistory passwordHistory : passwordHistories) {
            if (passwordEncoder.matches(newPassword, passwordHistory.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
