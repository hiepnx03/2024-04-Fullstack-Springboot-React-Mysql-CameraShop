package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.converter.CategoryConverter;
import com.example.camerashop_be.dto.CategoryDTO;
import com.example.camerashop_be.entity.Category;
import com.example.camerashop_be.repository.CategoryRepo;
import com.example.camerashop_be.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepo categoryRepo;
    private final CategoryConverter categoryConverter;

    @Override
    public Page<CategoryDTO> getAll(Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        if (status == null) {
            return categoryConverter.convertToDTO(categoryRepo.findAll(pageable));
        }
        return categoryConverter.convertToDTO(categoryRepo.findAllByStatus(status, pageable));
    }

    @Override
    public CategoryDTO add(CategoryDTO categoryDTO) {
        try {
            Category category = categoryConverter.convertToEntity(categoryDTO);
            Category categoryAdded = categoryRepo.save(category);
            return categoryConverter.convertToDto(categoryAdded);
        } catch (Exception e) {
            throw new RuntimeException("Category add failed!");
        }
    }

    @Override
    public CategoryDTO edit(CategoryDTO categoryDTO) {
        try {
            Category category = categoryRepo.findById(categoryDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Category " + categoryDTO.getId() + " does not exist!"));
            BeanUtils.copyProperties(categoryDTO, category, "id");
            Category categoryAdded = categoryRepo.save(category);
            return categoryConverter.convertToDto(categoryAdded);
        } catch (Exception e) {
            throw new RuntimeException("Category edit failed!");
        }
    }
}
