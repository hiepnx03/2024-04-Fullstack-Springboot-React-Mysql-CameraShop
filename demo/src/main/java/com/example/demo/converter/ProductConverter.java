package com.example.demo.converter;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProductConverter {
    private final CategoryRepository categoryRepository;

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());

        dto.setCategoryIds(product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        dto.setImages(product.getImages().stream()
                .map(ImageConverter::toDTO)
                .collect(Collectors.toSet()));
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        // Không thiết lập categories và images ở đây, sẽ được thiết lập trong service
        return product;
    }
}
