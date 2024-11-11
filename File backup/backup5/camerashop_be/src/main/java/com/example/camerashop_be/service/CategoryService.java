package com.example.camerashop_be.service;


import com.example.camerashop_be.converter.Converter;
import com.example.camerashop_be.dto.CategoryDTO;
import com.example.camerashop_be.entity.Category;
import com.example.camerashop_be.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Converter converter;

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = converter.convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return converter.convertToDto(savedCategory);
    }

    public CategoryDTO getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return converter.convertToDto(category);
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public CategoryDTO updateCategory(int id, CategoryDTO categoryDTO) {
        Category category = converter.convertToEntity(categoryDTO);
        category.setIdCategory(id);
        Category updatedCategory = categoryRepository.save(category);
        return converter.convertToDto(updatedCategory);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
