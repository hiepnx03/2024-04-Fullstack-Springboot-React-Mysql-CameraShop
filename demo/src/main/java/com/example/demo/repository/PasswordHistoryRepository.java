package com.example.demo.repository;

import com.example.demo.entity.PasswordHistory;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    List<PasswordHistory> findByUser(User user); // Tìm các mật khẩu lịch sử của người dùng
}
