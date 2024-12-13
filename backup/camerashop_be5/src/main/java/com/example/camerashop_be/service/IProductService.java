package com.example.camerashop_be.service;


import com.example.camerashop_be.dto.BestSellingProduct;
import com.example.camerashop_be.dto.request.ProductRequest;
import com.example.camerashop_be.dto.response.ProductResponse;
import com.example.camerashop_be.entity.Product;
import com.example.camerashop_be.repository.specs.ProductSpecification;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    Page<ProductResponse> getAllByCategoryId(Long id, Long price, Integer status, Integer page, Integer size);

    Page<ProductResponse> getAllByCategorySlug(String slug, Long price, Integer status, Integer page, Integer size);

    Page<ProductResponse> getAll(Long price, Integer status, Integer page, Integer size);

    Page<ProductResponse> search(String key, Integer page, Integer size);

    ProductResponse getById(Long id);

    Product getProductById(Long id);

    ProductResponse getBySlug(String slug);

    ProductResponse add(ProductRequest productRequest);

    List<Product> add(List<Product> products);

    ProductResponse edit(ProductRequest productRequest);

    Page<ProductResponse> filter(ProductSpecification productSpecification, int page, int size);

    Page<BestSellingProduct> getBestSellingInMonth(Integer month);
}
