package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


public interface ProductService {
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO);
    public Optional<ProductDTO> getProductById(Long id);
    public List<ProductDTO> getAllProducts();
    public void deleteProduct(Long id);
}
