package com.example.laptop_be.repository;

import com.example.laptop_be.entity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbacksRepository extends JpaRepository<Feedbacks ,Integer> {
}
