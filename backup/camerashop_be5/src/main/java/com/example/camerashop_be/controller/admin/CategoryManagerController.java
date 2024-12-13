package com.example.camerashop_be.controller.admin;


import com.example.camerashop_be.dto.CategoryDTO;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.service.ICategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/category")
public class CategoryManagerController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            Page<CategoryDTO> categoryDTOS = categoryService.getAll(null, page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get categories successful!", categoryDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseObject> add(@RequestBody CategoryDTO categoryDTO) throws JsonProcessingException {
        try {
            CategoryDTO categoryResponse;
            if (categoryDTO.getId() != null) {
                categoryResponse = categoryService.edit(categoryDTO);
            } else {
                categoryResponse = categoryService.add(categoryDTO);
            }
            return ResponseEntity.ok().body(new ResponseObject("ok", "Update category successfully!", categoryResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
