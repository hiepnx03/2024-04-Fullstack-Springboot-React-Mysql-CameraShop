package com.example.demo.controller.admin;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/admin/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            ResponseObject responseObject = new ResponseObject("ok", "Products retrieved successfully", products);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("error", "Failed to retrieve products: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Lấy sản phẩm theo ID
    @GetMapping("/paged")
    public Page<ProductDTO> getAllProductsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productService.getAllProductsPaged(page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) { // Check if the product is null
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("error", "Product with ID " + id + " not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("ok", "Product retrieved successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to retrieve product: " + e.getMessage(), null));
        }
    }


    @PostMapping
    public ResponseEntity<ResponseObject> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.addProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("ok", "Product created successfully", createdProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to create product: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        try {
            boolean isDeleted = productService.deleteProduct(id);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("error", "Product with ID " + id + " not found", null));
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to delete product: " + e.getMessage(), null));
        }
    }

    @GetMapping("/search")
    public Page<ProductDTO> searchProductsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.searchProductsByName(name, page, size);
    }

    @GetMapping("/filter-by-price")
    public Page<ProductDTO> filterProductsByPrice(
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "Double.MAX_VALUE") double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productService.filterProductsBySellPrice(minPrice, maxPrice, page, size, sortBy, direction);
    }

    @GetMapping("/filter-by-category-native")
    public Page<ProductDTO> filterProductsByCategoryNative(
            @RequestParam Set<Long> categoryIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productService.filterProductsByCategoryNative(categoryIds, page, size, sortBy, direction);
    }

    @GetMapping("/filter-by-category-and-price-native")
    public Page<ProductDTO> filterProductsByCategoryAndPriceNative(
            @RequestParam Set<Long> categoryIds,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "Double.MAX_VALUE") double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productService.filterProductsByCategoryAndPriceNative(categoryIds, minPrice, maxPrice, page, size, sortBy, direction);
    }
}
