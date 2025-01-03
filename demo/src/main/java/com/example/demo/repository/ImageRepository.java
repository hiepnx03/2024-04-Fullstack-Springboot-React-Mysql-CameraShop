package com.example.demo.repository;

import com.example.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i JOIN FETCH i.product WHERE i.product.id = :productId")
    List<Image> findAllImagesByProductId(@Param("productId") Long productId);


    boolean existsByUrl(String url);
}
