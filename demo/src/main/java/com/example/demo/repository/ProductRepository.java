package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by the name of the category
//    List<Product> findByCategory_Name(String categoryName);
    List<Product> findByCategories_Id(Long categoryId); // Tìm sản phẩm theo ID danh mục
    List<Product> findByCategories_Name(String categoryName);
    // Find products by the ID of the category
//    List<Product> findByCategory_Id(Long id);
}

