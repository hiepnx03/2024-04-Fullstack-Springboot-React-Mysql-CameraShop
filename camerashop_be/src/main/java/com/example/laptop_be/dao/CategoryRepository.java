package com.example.laptop_be.dao;

import com.example.laptop_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "genre")
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
