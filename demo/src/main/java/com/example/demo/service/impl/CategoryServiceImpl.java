package com.example.demo.service.impl;


import com.example.demo.converter.CategoryConverter;
import com.example.demo.converter.ProductConverter;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryConverter categoryConverter;
    private final ProductConverter productConverter;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, CategoryConverter categoryConverter, ProductConverter productConverter) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryConverter = categoryConverter;
        this.productConverter = productConverter;
    }


    //    public List<CategoryDTO> findAll() {
//        List<Category> categories = categoryRepository.findAll();
//        return categories.stream()
//                .map(categoryConverter::convertToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAllCategoriesWithProducts(); // Ensure products are fetched eagerly
        return categories.stream()
                .map(categoryConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryConverter::convertToDto)
                .orElse(null);  // Hoặc có thể trả về ResponseEntity hoặc Exception nếu không tìm thấy
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        // Fetch products by their IDs in the DTO (if necessary)
        Set<Product> products = categoryDTO.getProductIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId)))
                .collect(Collectors.toSet());

        // Convert DTO to entity
        Category category = categoryConverter.convertToEntity(categoryDTO, products);

        // Save category entity to the database
        Category savedCategory = categoryRepository.save(category);

        // Convert the saved entity back to DTO and return
        return categoryConverter.convertToDto(savedCategory);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategories_Id(categoryId);
        return products.stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDTO> getAll(Integer status,Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        if(status==null){
            return categoryConverter.convertToDTO(categoryRepository.findAll(pageable));
        }
        return categoryConverter.convertToDTO(categoryRepository.findAllByStatus(status,pageable));
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        // Check if the category exists by ID
        Category existingCategory = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryDTO.getId()));

        // Fetch products by their IDs in the DTO (if necessary)
        Set<Product> products = categoryDTO.getProductIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId)))
                .collect(Collectors.toSet());

        // Update the existing category's fields with the new data
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setDescription(categoryDTO.getDescription());
        existingCategory.setImage(categoryDTO.getImage());
        existingCategory.setActive(categoryDTO.isActive());
        existingCategory.setDeleted(categoryDTO.isDeleted());
        existingCategory.setEditable(categoryDTO.isEditable());
        existingCategory.setVisible(categoryDTO.isVisible());
        existingCategory.setProducts(products); // Update the associated products

        // Save the updated category entity to the database
        Category updatedCategory = categoryRepository.save(existingCategory);

        // Convert the updated entity back to DTO and return
        return categoryConverter.convertToDto(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        // Xóa sản phẩm liên kết với danh mục
        categoryRepository.deleteCategoryAndAssociationsById(id);
    }

    @Override
    public Page<Category> searchCategories(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findByNameContainingIgnoreCaseNative(keyword, pageable);
    }


}
