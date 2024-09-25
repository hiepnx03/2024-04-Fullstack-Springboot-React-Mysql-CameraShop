package com.example.laptop_be.repository;

import com.example.laptop_be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:3000")
@RepositoryRestResource(path = "products")
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
