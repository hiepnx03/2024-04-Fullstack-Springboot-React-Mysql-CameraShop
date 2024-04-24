package com.example.laptop_be.controller;

import com.example.laptop_be.dao.CategoryRepository;
import com.example.laptop_be.entity.Category;
import com.example.laptop_be.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
public class CategoryController {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    // Lấy danh sách tất cả danh mục sản phẩm
//    @GetMapping
//    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
//    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//    // Lấy thông tin của một danh mục sản phẩm dựa trên id
//    @GetMapping("/{categoryId}")
//    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
//    public Category getCategoryById(@PathVariable int categoryId) {
//        return categoryRepository.findById(categoryId).orElse(null);
//    }
//
//    // Tạo mới một danh mục sản phẩm
//    @PostMapping
//    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
//    public Category createCategory(@RequestBody Category category) {
//        return categoryRepository.save(category);
//    }
//
//    // Cập nhật thông tin của một danh mục sản phẩm
//    @PutMapping("/{categoryId}")
//    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
//    public Category updateCategory(@PathVariable int categoryId, @RequestBody Category categoryDetails) {
//        Category category = categoryRepository.findById(categoryId).orElse(null);
//        if (category != null) {
//            category.setCategoryName(categoryDetails.getCategoryName());
//            return categoryRepository.save(category);
//        }
//        return null;
//    }
//
//    // Xóa một danh mục sản phẩm
//    @DeleteMapping("/{categoryId}")
//    @CrossOrigin(origins = "http://localhost:3000") // Cho phép các yêu cầu từ domain này
//    public void deleteCategory(@PathVariable int categoryId) {
//        categoryRepository.deleteById(categoryId);
//    }

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping("")
//    public ResponseEntity<List<Category>> getAllCategories() {
//        List<Category> categories = categoryService.getAllCategories();
//        return new ResponseEntity<>(categories, HttpStatus.OK);
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
//        Category category = categoryService.getCategoryById(id);
//        if (category != null) {
//            return new ResponseEntity<>(category, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
//        categoryService.deleteCategory(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }


    //////////////////////////// JPQL /////////////////////////
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategoriesJPQL (){
        List<Category> categories = categoryService.getAllCategoriesJPQL();
        return new ResponseEntity<>(categories , HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryByIdJPQL (@PathVariable int id){
        Category category = categoryService.getCategoryByIdJPQL(id);
        if(category != null){
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryByIdJPQL(@PathVariable int id) {
        categoryService.deleteCategoryByIdJPQL(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updateCategoryNameByIdJPQL(@PathVariable int id, @RequestParam String categoryName) {
//        categoryService.updateCategoryNameByIdJPQL(id, categoryName);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping()
//    public ResponseEntity<Void> createCategoryJPQL(@RequestParam String categoryName) {
//        categoryService.createCategoryJPQL(categoryName);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

}
