package com.example.demo.controller.admin;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.demo.exception.EntityNotFoundException;


@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllCategories() {
        try {
            List<CategoryDTO> categories = categoryService.findAll();
            ResponseObject responseObject = new ResponseObject("ok", "Categories retrieved successfully", categories);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("error", "Failed to retrieve categories: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDTO categoryDTO = categoryService.findById(id);
            if (categoryDTO == null) {
                throw new EntityNotFoundException("Category with ID " + id + " not found");
            }
            ResponseObject responseObject = new ResponseObject("ok", "Category found successfully", categoryDTO);
            return ResponseEntity.ok(responseObject);
        } catch (EntityNotFoundException ex) {
            ResponseObject errorResponse = new ResponseObject("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("error", "Failed to fetch category: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PostMapping
    public ResponseEntity<ResponseObject> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO createdCategory = categoryService.save(categoryDTO);
            ResponseObject responseObject = new ResponseObject("ok", "Category created successfully", createdCategory);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("error", "Failed to create category: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        try {
            categoryDTO.setId(id);
            CategoryDTO updatedCategory = categoryService.update(categoryDTO);
            if (updatedCategory == null) {
                throw new EntityNotFoundException("Category with ID " + id + " not found");
            }
            ResponseObject responseObject = new ResponseObject("ok", "Category updated successfully", updatedCategory);
            return ResponseEntity.ok(responseObject);
        } catch (EntityNotFoundException ex) {
            ResponseObject errorResponse = new ResponseObject("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("error", "Failed to update category: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            ResponseObject responseObject = new ResponseObject("ok", "Category deleted successfully: ", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        } catch (EntityNotFoundException ex) {
            ResponseObject errorResponse = new ResponseObject("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("error", "Failed to delete category: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/search")
    public Page<Category> searchCategories(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        return categoryService.searchCategories(keyword, page, size);
    }
}
