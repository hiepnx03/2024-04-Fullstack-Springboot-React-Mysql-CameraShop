package com.example.camerashop_be.controller;


import com.example.camerashop_be.dto.CategoryDTO;
import com.example.camerashop_be.entity.EStatus;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.service.impl.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            Page<CategoryDTO> categoryDTOS = categoryService.getAll(EStatus.ACTIVE.getName(), page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get image successful!", categoryDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

        }
    }

}
