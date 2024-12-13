package com.example.laptop_be.repository;

import com.example.laptop_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "category")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
