package com.example.laptop_be.repository;

import com.example.laptop_be.entity.FavoriteProduct;
import com.example.laptop_be.entity.Product;
import com.example.laptop_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "favorite-product")
public interface FavoriteBookRepository extends JpaRepository<FavoriteProduct, Integer> {
    public FavoriteProduct findFavoriteProductByProductAndUser(Product product, User user);
    public List<FavoriteProduct> findFavoriteProductsByUser(User user);
}
