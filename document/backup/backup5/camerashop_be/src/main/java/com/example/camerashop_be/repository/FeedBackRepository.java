package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<Feedbacks, Integer> {
    long countBy();
}
