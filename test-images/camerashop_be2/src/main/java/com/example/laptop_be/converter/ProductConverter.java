package com.example.laptop_be.converter;


import com.example.laptop_be.dto.ProductDTO;
import com.example.laptop_be.entity.Image;
import com.example.laptop_be.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setImageUrls(product.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList()));
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        List<Image> images = dto.getImageUrls().stream()
                .map(url -> {
                    Image image = new Image();
                    image.setUrl(url);
                    image.setProduct(product);
                    return image;
                })
                .collect(Collectors.toList());
        product.setImages(images);
        return product;
    }
}