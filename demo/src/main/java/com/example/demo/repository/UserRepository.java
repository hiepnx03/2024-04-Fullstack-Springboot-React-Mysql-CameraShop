package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    //    Optional<User> findByUsername(String username);
    User findByRefreshToken(String refreshToken);

    User findByEmail(String email);

    User findByUserName(String username);

    User findByEmailAndStatus(String userName, Integer status);

    User findByVerificationCode(String verificationCode);
}

