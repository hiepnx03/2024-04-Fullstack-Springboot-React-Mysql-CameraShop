package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by the name of the category
//    List<Product> findByCategory_Name(String categoryName);
    List<Product> findByCategories_Id(Long categoryId); // Tìm sản phẩm theo ID danh mục

    List<Product> findByCategories_Name(String categoryName);
    // Find products by the ID of the category
//    List<Product> findByCategory_Id(Long id);

    // Tìm tất cả sản phẩm với phân trang
    Page<Product> findAll(Pageable pageable);

    // Tìm sản phẩm theo tên (dạng 'like') với phân trang
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "JOIN product_category pc ON p.id = pc.product_id " +
            "WHERE pc.category_id IN (:categoryIds)", nativeQuery = true)
    Page<Product> findByCategoryIds(@Param("categoryIds") Set<Long> categoryIds, Pageable pageable);


    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "JOIN product_category pc ON p.id = pc.product_id " +
            "WHERE pc.category_id IN (:categoryIds) " +
            "AND p.price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    Page<Product> findByCategoryIdsAndPriceBetween(
            @Param("categoryIds") Set<Long> categoryIds,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable);

}

