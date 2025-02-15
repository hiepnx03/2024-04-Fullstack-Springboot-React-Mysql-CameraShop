package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
