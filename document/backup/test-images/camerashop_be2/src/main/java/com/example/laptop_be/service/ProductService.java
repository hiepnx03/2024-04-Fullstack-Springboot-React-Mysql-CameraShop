package com.example.laptop_be.service;

import com.example.laptop_be.converter.ProductConverter;
import com.example.laptop_be.dto.ProductDTO;
import com.example.laptop_be.entity.Image;
import com.example.laptop_be.entity.Product;
import com.example.laptop_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public ProductService(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productConverter::toDTO)
                .orElse(null);
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productConverter.toEntity(productDTO);
        product = productRepository.save(product);
        return productConverter.toDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(productDTO.getName());

            // Xóa các ảnh cũ không còn trong danh sách mới
            List<String> newImageUrls = productDTO.getImageUrls();
            existingProduct.getImages().removeIf(image -> !newImageUrls.contains(image.getUrl()));

            // Thêm các ảnh mới vào sản phẩm
            List<Image> newImages = productDTO.getImageUrls().stream()
                    .filter(url -> existingProduct.getImages().stream().noneMatch(image -> image.getUrl().equals(url)))
                    .map(url -> {
                        Image image = new Image();
                        image.setUrl(url);
                        image.setProduct(existingProduct);
                        return image;
                    }).collect(Collectors.toList());

            existingProduct.getImages().addAll(newImages);
            Product updatedProduct = productRepository.save(existingProduct);
            return productConverter.toDTO(updatedProduct);
        }).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}