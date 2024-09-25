package com.example.laptop_be.repository;

import com.example.laptop_be.entity.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {
}
