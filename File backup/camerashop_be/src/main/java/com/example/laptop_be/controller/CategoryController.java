package com.example.laptop_be.controller;

import com.example.laptop_be.dao.CategoryRepository;
import com.example.laptop_be.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy danh sách tất cả danh mục sản phẩm
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lấy thông tin của một danh mục sản phẩm dựa trên id
    @GetMapping("/{categoryId}")
    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
    public Category getCategoryById(@PathVariable int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    // Tạo mới một danh mục sản phẩm
    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // Cập nhật thông tin của một danh mục sản phẩm
    @PutMapping("/{categoryId}")
    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
    public Category updateCategory(@PathVariable int categoryId, @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            category.setCategoryName(categoryDetails.getCategoryName());
            return categoryRepository.save(category);
        }
        return null;
    }

    // Xóa một danh mục sản phẩm
    @DeleteMapping("/{categoryId}")
    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
    public void deleteCategory(@PathVariable int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
