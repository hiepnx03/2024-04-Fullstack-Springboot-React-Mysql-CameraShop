package com.example.demo.converter;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class CategoryConverter {

    private final ModelMapper modelMapper;

    public CategoryConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Page<CategoryDTO> convertToDTO(Page<Category> pageEntity) {
        if (pageEntity == null) {
            return null;
        }
        return pageEntity.map(this::convertToDto);
    }
//
//    public CategoryDTO convertToDto(Category category) {
//        return modelMapper.map(category, CategoryDTO.class);
//    }
//
//    public Category convertToEntity(CategoryDTO categoryDTO) {
//        return modelMapper.map(categoryDTO, Category.class);
//    }

    public CategoryDTO convertToDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImage(category.getImage());
        dto.setActive(category.isActive());
        dto.setDeleted(category.isDeleted());
        dto.setEditable(category.isEditable());
        dto.setVisible(category.isVisible());

        dto.setProductIds(category.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet())); // Chuyển đổi sản phẩm thành ID

        return dto;
    }

    public Category convertToEntity(CategoryDTO dto, Set<Product> products) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImage(dto.getImage());
        category.setActive(dto.isActive());
        category.setDeleted(dto.isDeleted());
        category.setEditable(dto.isEditable());
        category.setVisible(dto.isVisible());

        category.setProducts(products); // Thiết lập sản phẩm liên kết

        return category;
    }
}