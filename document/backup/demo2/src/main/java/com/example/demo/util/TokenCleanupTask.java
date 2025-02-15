package com.example.demo.util;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class TokenCleanupTask {

    private final UserRepository userRepository;

    public TokenCleanupTask(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Chạy mỗi ngày lúc nửa đêm
    public void cleanExpiredTokens() {
        List<User> expiredUsers = userRepository.findAll().stream()
                .filter(user -> user.getExpiryDate() != null && user.getExpiryDate().isBefore(Instant.now()))
                .toList();

        for (User user : expiredUsers) {
            user.setRefreshToken(null);
            user.setExpiryDate(null);
            userRepository.save(user);
        }
    }
}
