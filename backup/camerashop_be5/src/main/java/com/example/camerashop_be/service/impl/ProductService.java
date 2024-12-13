package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.converter.ProductConverter;
import com.example.camerashop_be.dto.BestSellingProduct;
import com.example.camerashop_be.dto.request.ProductRequest;
import com.example.camerashop_be.dto.response.ProductResponse;
import com.example.camerashop_be.entity.Product;
import com.example.camerashop_be.repository.ProductRepo;
import com.example.camerashop_be.repository.specs.ProductSpecification;
import com.example.camerashop_be.service.IProductService;
import com.example.camerashop_be.service.IStorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepo productRepo;
    private final ProductConverter productConverter;
    private final IStorageService storageService;

    @Override
    public Page<ProductResponse> getAllByCategoryId(Long id, Long price, Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
//		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        if (status == null) {
            return productConverter.convertToResponse(productRepo.findAllByCategoryIdAndPriceLessThanEqual(id, price, pageable));
        }
        return productConverter.convertToResponse(productRepo.findAllByCategoryIdAndPriceLessThanEqualAndStatus(id, price, status, pageable));

    }

    @Override
    public Page<ProductResponse> getAllByCategorySlug(String slug, Long price, Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
//		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        if (status == null) {
            return productConverter.convertToResponse(productRepo.findAllByCategorySlugAndPriceLessThanEqual(slug, price, pageable));
        }
        return productConverter.convertToResponse(productRepo.findAllByCategorySlugAndPriceLessThanEqualAndStatus(slug, price, status, pageable));

    }

    @Override
    public Page<ProductResponse> getAll(Long price, Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
//		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        if (status == null) {
            return productConverter.convertToResponse(productRepo.findAllByPriceLessThanEqual(price, pageable));
        }
        return productConverter.convertToResponse(productRepo.findAllByPriceLessThanEqualAndStatus(price, status, pageable));

    }

    @Override
    public Page<ProductResponse> search(String key, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return productConverter.convertToResponse(productRepo.findAllByNameOrCategoryName(key, pageable));
    }

    @Override
    public ProductResponse getById(Long id) {
        try {
            return productConverter.convertToResponse(productRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product " + id + " does not exist!")));
        } catch (Exception e) {
            throw new RuntimeException("Product " + id + " is not found!");
        }

    }

    @Override
    public Product getProductById(Long id) {
        try {
            return productRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product " + id + " does not exist!"));
        } catch (Exception e) {
            throw new RuntimeException("Product " + id + " is not found!");
        }

    }

    @Override
    public ProductResponse getBySlug(String slug) {
        Product product = productRepo.findBySlug(slug);
        if (product != null) {
            return productConverter.convertToResponse(product);
        }
        return null;
    }

    @Override

    public ProductResponse add(ProductRequest productRequest) {
        try {
            Product product = productConverter.convertToEntity(productRequest);
            Product productAdded = productRepo.save(product);
            return productConverter.convertToResponse(productAdded);
        } catch (Exception e) {
            throw new RuntimeException("Add product failed!");
        }
    }

    @Override

    public List<Product> add(List<Product> products) {
        try {
            return productRepo.saveAll(products);
        } catch (Exception e) {
            throw new RuntimeException("Save product failed!");
        }
    }

    @Override
    public ProductResponse edit(ProductRequest productRequest) {
        try {
            Product product = productRepo.findById(productRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product " + productRequest.getId() + " does not exist!"));
            BeanUtils.copyProperties(productRequest, product, "id");
            product.getCategory().setId(productRequest.getCategory().getId());
            Product productAdded = productRepo.save(product);
            return productConverter.convertToResponse(productAdded);
        } catch (Exception e) {
            throw new RuntimeException("Product failed!");
        }
    }


    @Override
    public Page<ProductResponse> filter(ProductSpecification productSpecification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return productConverter.convertToResponse(productRepo.findAll(productSpecification, pageable));
    }

    @Override
    public Page<BestSellingProduct> getBestSellingInMonth(Integer month) {
        Pageable pageable = PageRequest.of(0, 5);
        return productRepo.getBestSellingInMonth(month, pageable);
    }
}
