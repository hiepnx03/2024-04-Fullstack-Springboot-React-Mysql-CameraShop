package com.example.laptop_be.dao;

import com.example.laptop_be.entity.Image;
import com.example.laptop_be.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.awt.print.Book;
import java.util.List;

@RepositoryRestResource(path = "images")
public interface ImageRepository extends JpaRepository<Image, Integer> {
    public List<Image> findImagesByProduct(Product product);
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.isThumbnail = false AND i.product.idProduct = :productId")
    public void deleteImagesWithFalseThumbnailByProductId(@Param("productId") int productId);
}
