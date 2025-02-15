package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);  // Sửa lại kiểu trả về là Optional<Category>

    @Query("SELECT c FROM Category c JOIN FETCH c.products")
    List<Category> findAllCategoriesWithProducts();
//    Category findByName(String name);


    // Tìm kiếm theo tên danh mục (case-insensitive) với Native Query và phân trang
    @Query(value = "SELECT * FROM category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(*) FROM category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true)
    Page<Category> findByNameContainingIgnoreCaseNative(String name, Pageable pageable);




    @Transactional
    @Modifying
    @Query("DELETE FROM Category m WHERE m.id = :id")
    void deleteCategoryAndAssociationsById(@Param("id") Long id);

}
