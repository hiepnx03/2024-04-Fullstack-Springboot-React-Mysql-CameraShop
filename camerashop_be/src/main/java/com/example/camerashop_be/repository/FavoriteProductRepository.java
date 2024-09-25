package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {
}
