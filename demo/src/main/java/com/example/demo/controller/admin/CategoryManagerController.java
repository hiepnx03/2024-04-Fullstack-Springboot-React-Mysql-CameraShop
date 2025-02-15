package com.example.demo.controller.admin;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.EStatus;
import com.example.demo.entity.ResponseObject;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class CategoryManagerController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all categories with pagination", description = "Fetch categories with pagination by providing page and size parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            log.info("Fetching all categories, page: {}, size: {}", page, size);
            Page<CategoryDTO> categoryDTOS = categoryService.getAll(EStatus.ACTIVE.getName(), page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get categories successful!", categoryDTOS));
        } catch (EntityNotFoundException e) {
            log.error("Categories not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Categories not found", null));
        } catch (Exception e) {
            log.error("Error while fetching categories", e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));
        }
    }

    @Operation(summary = "Get all categories", description = "Fetch all categories without pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<ResponseObject> getAllCategories() {
        try {
            log.info("Fetching all categories without pagination");
            List<CategoryDTO> categories = categoryService.findAll();
            return ResponseEntity.ok(new ResponseObject("ok", "Categories retrieved successfully", categories));
        } catch (Exception e) {
            log.error("Error while retrieving categories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to retrieve categories: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Get category by ID", description = "Fetch a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategoryById(@PathVariable Long id) {
        try {
            log.info("Fetching category with ID: {}", id);
            CategoryDTO categoryDTO = categoryService.findById(id);
            if (categoryDTO == null) {
                throw new EntityNotFoundException("Category with ID " + id + " not found");
            }
            return ResponseEntity.ok(new ResponseObject("ok", "Category found successfully", categoryDTO));
        } catch (EntityNotFoundException ex) {
            log.error("Category not found with ID: {}", id, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", ex.getMessage(), null));
        } catch (Exception e) {
            log.error("Error while fetching category with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to fetch category: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Create a new category", description = "Create a new category in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ResponseObject> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            log.info("Creating new category: {}", categoryDTO);
            CategoryDTO createdCategory = categoryService.save(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("ok", "Category created successfully", createdCategory));
        } catch (Exception e) {
            log.error("Error while creating category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to create category: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Update an existing category", description = "Update an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        try {
            log.info("Updating category with ID: {}", id);
            categoryDTO.setId(id);
            CategoryDTO updatedCategory = categoryService.update(categoryDTO);
            if (updatedCategory == null) {
                throw new EntityNotFoundException("Category with ID " + id + " not found");
            }
            return ResponseEntity.ok(new ResponseObject("ok", "Category updated successfully", updatedCategory));
        } catch (EntityNotFoundException ex) {
            log.error("Category not found with ID: {}", id, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", ex.getMessage(), null));
        } catch (Exception e) {
            log.error("Error while updating category with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to update category: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Delete category by ID", description = "Delete a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) {
        try {
            log.info("Deleting category with ID: {}", id);
            categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Category deleted successfully", id));
        } catch (EntityNotFoundException ex) {
            log.error("Category not found with ID: {}", id, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", ex.getMessage(), null));
        } catch (Exception e) {
            log.error("Error while deleting category with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to delete category: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Search categories by keyword", description = "Search for categories using a keyword, supports pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public Page<Category> searchCategories(@RequestParam String keyword,
                                           @RequestParam int page,
                                           @RequestParam int size) {
        log.info("Searching for categories with keyword: {}", keyword);
        return categoryService.searchCategories(keyword, page, size);
    }
}
