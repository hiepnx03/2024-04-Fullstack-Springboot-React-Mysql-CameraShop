package com.example.demo.repository;

import com.example.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findById(long id);
    Optional<Payment> findByPayer(String payer);  // Tìm thanh toán theo tên người thanh toán

//    Payment findByPayer(String payer);
}
