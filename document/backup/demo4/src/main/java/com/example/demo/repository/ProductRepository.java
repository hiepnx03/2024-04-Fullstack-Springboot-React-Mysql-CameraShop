package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by the name of the category
    Product findByName(String name);

//    List<Product> findByCategory_Name(String categoryName);
    List<Product> findByCategories_Id(Long categoryId); // Tìm sản phẩm theo ID danh mục

    Page<Product> findAll(Pageable pageable);

    // Tìm sản phẩm theo tên (dạng 'like') với phân trang
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findBySellPriceBetween(Double minSellPrice, Double maxSellPrice, Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "JOIN product_category pc ON p.id = pc.product_id " +
            "WHERE pc.category_id IN (:categoryIds)", nativeQuery = true)
    Page<Product> findByCategoryIds(@Param("categoryIds") Set<Long> categoryIds, Pageable pageable);


    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "JOIN product_category pc ON p.id = pc.product_id " +
            "WHERE pc.category_id IN (:categoryIds) " +
            "AND p.SellPrice BETWEEN :minSellPrice AND :maxSellPrice", nativeQuery = true)
    Page<Product> findByCategoryIdsAndSellPriceBetween(
            @Param("categoryIds") Set<Long> categoryIds,
            @Param("minSellPrice") Double minSellPrice,
            @Param("maxSellPrice") Double maxSellPrice,
            Pageable pageable);

}

