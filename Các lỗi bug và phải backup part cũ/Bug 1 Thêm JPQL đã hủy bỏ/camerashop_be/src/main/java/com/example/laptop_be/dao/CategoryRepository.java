package com.example.laptop_be.dao;

import com.example.laptop_be.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

//@RepositoryRestResource(path = "category")
@Repository
@CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c")
    List<Category> getAllCategoriesJPQL();

    @Query("SELECT c FROM Category c WHERE c.idCategory = :id")
    Category getCategoryByIdJPQL(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.idCategory = :id")
    void deleteCategoryByIdJPQL(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.categoryName = :categoryName WHERE c.idCategory = :id")
    void updateCategoryNameByIdJPQL(@Param("id") int id, @Param("categoryName") String categoryName);
//    @Transactional
//    @Query("INSERT INTO Category (categoryName) VALUES (:categoryName)")
//    void createCategoryJPQL(@Param("categoryName") String categoryName);

//    @Query("INSERT INTO Category (categoryName) VALUES (:categoryName)")
//    void createCategoryJPQL(@Param("categoryName") String categoryName);

}
