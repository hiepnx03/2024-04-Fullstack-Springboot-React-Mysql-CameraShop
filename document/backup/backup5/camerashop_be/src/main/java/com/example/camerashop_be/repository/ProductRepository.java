package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:3000")
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
