package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ProductService {
    public ProductDTO addProduct(ProductDTO productDTO);
    public Optional<ProductDTO> getProductById(Long id);
    public List<ProductDTO> getAllProducts();
    public boolean deleteProduct(Long id);
    public Page<ProductDTO> getAllProductsPaged(int page, int size, String sortBy, String direction);
    Page<ProductDTO> searchProductsByName(String name, int page, int size);
    Page<ProductDTO> filterProductsBySellPrice(double minPrice, double maxPrice, int page, int size, String sortBy, String direction);
    Page<ProductDTO> filterProductsByCategoryNative(Set<Long> categoryIds, int page, int size, String sortBy, String direction);
    Page<ProductDTO> filterProductsByCategoryAndPriceNative(Set<Long> categoryIds, double minPrice, double maxPrice, int page, int size, String sortBy, String direction);
}
