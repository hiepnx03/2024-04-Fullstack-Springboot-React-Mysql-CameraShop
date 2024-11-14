package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.FavoriteProduct;
import com.example.camerashop_be.entity.Product;
import com.example.camerashop_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBookRepository extends JpaRepository<FavoriteProduct, Integer> {
    public FavoriteProduct findFavoriteProductByProductAndUser(Product product, User user);
    public List<FavoriteProduct> findFavoriteProductsByUser(User user);
}
