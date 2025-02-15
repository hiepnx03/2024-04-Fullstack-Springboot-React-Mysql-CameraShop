package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	Page<Category> findAllByStatus(Integer status,Pageable pageable);
}
