package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Image;
import com.example.camerashop_be.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    public List<Image> findImagesByProduct(Product product);
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.isThumbnail = false AND i.product.idProduct = :productId")
    public void deleteImagesWithFalseThumbnailByProductId(@Param("productId") int productId);
}
