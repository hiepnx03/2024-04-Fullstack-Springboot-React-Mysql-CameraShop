package com.example.laptop_be.service.product;

import com.example.laptop_be.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(int id);

    Product addProduct(Product product);


    ResponseEntity<?> addProduct1(Product product);
}
