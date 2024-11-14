package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);  // Sửa lại kiểu trả về là Optional<Category>

    @Query("SELECT c FROM Category c JOIN FETCH c.products")
    List<Category> findAllCategoriesWithProducts();
//    Category findByName(String name);
}
