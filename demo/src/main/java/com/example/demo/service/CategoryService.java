package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    @Transactional
    public List<CategoryDTO> findAll();
    public CategoryDTO findById(Long id) ;
    public CategoryDTO save(CategoryDTO categoryDTO) ;
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) ;
    public CategoryDTO update(CategoryDTO categoryDTO) ;
    public void delete(Long id) ;
}
