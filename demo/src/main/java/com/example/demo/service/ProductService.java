package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;


public interface ProductService {
    public ProductDTO addProduct(ProductDTO productDTO);
    public ProductDTO updateProduct(Long id, ProductDTO productDTO);
    public Product getProductById(Long id);
    public List<ProductDTO> getAllProducts();
    public boolean deleteProduct(Long id);
    public Page<ProductDTO> getAllProductsPaged(int page, int size, String sortBy, String direction);
    public ProductResponse getById(Long id);
    Page<ProductDTO> searchProductsByName(String name, int page, int size);
    Page<ProductDTO> filterProductsBySellPrice(double minPrice, double maxPrice, int page, int size, String sortBy, String direction);
    Page<ProductDTO> filterProductsByCategoryNative(Set<Long> categoryIds, int page, int size, String sortBy, String direction);
    Page<ProductDTO> filterProductsByCategoryAndPriceNative(Set<Long> categoryIds, double minPrice, double maxPrice, int page, int size, String sortBy, String direction);
    List<Product> add(List<Product> products);
    public Page<ProductResponse> getAll(Double sellPrice, Integer status, Integer page, Integer size);
    public Page<ProductResponse> getAllByCategorySlug(String slug,Double sellPrice, Integer status, Integer page, Integer size);

}
