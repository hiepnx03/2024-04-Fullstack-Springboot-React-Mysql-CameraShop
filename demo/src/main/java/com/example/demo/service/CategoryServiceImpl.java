package com.example.demo.service;


import com.example.demo.converter.CategoryConverter;
import com.example.demo.converter.ProductConverter;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryConverter categoryConverter;
    private final ProductConverter productConverter;


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
                .map(categoryConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryConverter::convertToDTO)
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
        return categoryConverter.convertToDTO(savedCategory);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategories_Id(categoryId);
        return products.stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList());
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
        return categoryConverter.convertToDTO(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        // Check if the category exists by ID
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        // Delete the category if it exists
        categoryRepository.delete(existingCategory);

//
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
//
//        // Cập nhật các Product liên kết với Category này bằng cách đặt status_category thành false
//        List<Product> productsInCategory = productRepository.findByCategory(id);
//        for (Product product : productsInCategory) {
//            product.setStatusCategory(false);  // Đánh dấu sản phẩm không còn thuộc category này
//            productRepository.save(product);  // Lưu lại product đã cập nhật
//        }
//
//        // Sau đó có thể xóa Category
//        categoryRepository.delete(category);
    }


}
