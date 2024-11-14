package com.example.demo.service;

import com.example.demo.converter.ImageConverter;
import com.example.demo.converter.ProductConverter;
import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;


    // Tạo sản phẩm mới
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productConverter.toEntity(productDTO); // Sửa đổi ở đây


        // Thiết lập danh mục
        if (productDTO.getCategoryIds() != null) {
            Set<Category> categories = productDTO.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new IllegalArgumentException("Category not found with id " + categoryId)))
                    .collect(Collectors.toSet());
            product.setCategories(categories);
        }

        // Thiết lập hình ảnh
        Set<Image> images = new HashSet<>();
        if (productDTO.getImages() != null) {
            for (ImageDTO imageDTO : productDTO.getImages()) {
                Image image = ImageConverter.toEntity(imageDTO);
                image.setProduct(product); // Thiết lập sản phẩm cho hình ảnh
                images.add(image);
            }
        }
        product.setImages(images);

        // Lưu sản phẩm vào cơ sở dữ liệu
        product = productRepository.save(product);
        return productConverter.toDTO(product);
    }

    // Lấy sản phẩm theo ID
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productConverter::toDTO);
    }

    // Lấy tất cả sản phẩm
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList());
    }

    // Xóa sản phẩm theo ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
