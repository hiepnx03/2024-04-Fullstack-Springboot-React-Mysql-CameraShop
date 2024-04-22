package com.example.laptop_be.service;

import com.example.laptop_be.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    public Optional<Product> getProductById(Long id);

    public Product saveProduct(Product product);
}
