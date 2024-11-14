package com.example.camerashop_be.service;

import com.example.camerashop_be.dto.CategoryDTO;
import org.springframework.data.domain.Page;

public interface ICategoryService {
	Page<CategoryDTO> getAll(Integer status, Integer page, Integer size);
	CategoryDTO add(CategoryDTO categoryDTO);
	CategoryDTO edit(CategoryDTO categoryDTO);
}
