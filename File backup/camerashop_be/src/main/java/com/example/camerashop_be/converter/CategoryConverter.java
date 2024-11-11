package com.example.camerashop_be.converter;

import com.example.camerashop_be.dto.CategoryDTO;
import com.example.camerashop_be.entity.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryConverter {
	private final ModelMapper modelMapper;

	public CategoryDTO convertToDto(Category entity) {
		return modelMapper.map(entity, CategoryDTO.class);
	}

	public Category convertToEntity(CategoryDTO dto)
	{
		return modelMapper.map(dto, Category.class);
	}
	public Page<CategoryDTO> convertToDTO(Page<Category> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToDto);
	}
}
