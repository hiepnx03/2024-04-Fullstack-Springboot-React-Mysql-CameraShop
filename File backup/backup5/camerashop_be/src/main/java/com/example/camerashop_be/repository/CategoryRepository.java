package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
