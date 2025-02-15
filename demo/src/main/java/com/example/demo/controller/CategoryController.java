package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.EStatus;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Retrieves all active categories with pagination")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request due to invalid parameters")
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            Page<CategoryDTO> categoryDTOS = categoryService.getAll(EStatus.ACTIVE.getName(), page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Categories retrieved successfully", categoryDTOS));
        } catch (Exception e) {
            log.error("Error occurred while retrieving categories", e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}