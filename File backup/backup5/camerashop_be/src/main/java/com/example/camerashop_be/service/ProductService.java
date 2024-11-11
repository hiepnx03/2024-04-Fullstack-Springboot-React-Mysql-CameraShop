package com.example.camerashop_be.service;


import com.example.camerashop_be.converter.Converter;
import com.example.camerashop_be.dto.ProductDTO;
import com.example.camerashop_be.entity.Category;
import com.example.camerashop_be.entity.Image;
import com.example.camerashop_be.entity.Product;
import com.example.camerashop_be.repository.CategoryRepository;
import com.example.camerashop_be.repository.ImageRepository;
import com.example.camerashop_be.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Converter converter;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = converter.convertToEntity(productDTO);

        // Setting categories
        if (productDTO.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(productDTO.getCategoryIds());
            product.setCategoryList(categories);
        }

        // Save the product first to get the ID
        Product savedProduct = productRepository.save(product);

        // Setting images
        if (productDTO.getImageUrls() != null) {
            List<Image> images = productDTO.getImageUrls().stream().map(url -> {
                Image image = new Image();
                image.setUrlImage(url);
                image.setProduct(savedProduct);
                return image;
            }).collect(Collectors.toList());
            imageRepository.saveAll(images);
        }

        return converter.convertToDto(savedProduct);
    }

    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id).orElse(null);
        return converter.convertToDto(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Product product = converter.convertToEntity(productDTO);
        product.setIdProduct(id);

        // Setting categories
        if (productDTO.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(productDTO.getCategoryIds());
            product.setCategoryList(categories);
        }

        // Save the product first to get the ID
        Product updatedProduct = productRepository.save(product);

        // Setting images
        if (productDTO.getImageUrls() != null) {
            List<Image> images = productDTO.getImageUrls().stream().map(url -> {
                Image image = new Image();
                image.setUrlImage(url);
                image.setProduct(updatedProduct);
                return image;
            }).collect(Collectors.toList());
            imageRepository.saveAll(images);
        }

        return converter.convertToDto(updatedProduct);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
