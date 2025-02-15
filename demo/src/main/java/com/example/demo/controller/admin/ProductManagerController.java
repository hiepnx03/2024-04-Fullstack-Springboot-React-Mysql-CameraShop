package com.example.demo.controller.admin;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.EStatus;
import com.example.demo.entity.Product;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.ProductService;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/products")
@AllArgsConstructor
@Slf4j
public class ProductManagerController {
    private final ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Failed to retrieve products")
    @GetMapping
    public ResponseEntity<ResponseObject> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            ResponseObject responseObject = new ResponseObject("ok", "Products retrieved successfully", products);
            log.info("Retrieved {} products", products.size());  // Log khi lấy danh sách sản phẩm thành công
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            log.error("Error retrieving products: {}", e.getMessage(), e);  // Log khi có lỗi
            ResponseObject errorResponse = new ResponseObject("error", "Failed to retrieve products: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Get products by category slug and price", description = "Retrieve a list of products based on category slug and price")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllByCategorySlugAndPrice(
            @RequestParam(name = "categorySlug", required = false) String slug,
            @RequestParam(name = "sellPrice", required = false) Double sellPrice,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<ProductResponse> products;
            if (slug == null || slug.equalsIgnoreCase("null") || slug.equalsIgnoreCase("all")) {
                products = productService.getAll(sellPrice, EStatus.ACTIVE.getName(), page, size);
            } else {
                products = productService.getAllByCategorySlug(slug, sellPrice, EStatus.ACTIVE.getName(), page, size);
            }

            log.info("Retrieved products for categorySlug: {} and price: {}", slug, sellPrice);  // Log khi lấy sản phẩm theo categorySlug và giá
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", products));

        } catch (Exception e) {
            log.error("Error retrieving products by category and price: {}", e.getMessage(), e);  // Log khi có lỗi
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @Operation(summary = "Get all products with pagination", description = "Retrieve a paginated list of products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @GetMapping("/paged")
    public Page<ProductDTO> getAllProductsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productService.getAllProductsPaged(page, size, sortBy, direction);
    }

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) { // Kiểm tra nếu sản phẩm không tồn tại
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("error", "Product with ID " + id + " not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("ok", "Product retrieved successfully", product));
        } catch (Exception e) {
            log.error("Error retrieving product with ID {}: {}", id, e.getMessage(), e);  // Log khi có lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to retrieve product: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Create a new product", description = "Create a new product in the system")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "500", description = "Failed to create product")
    @PostMapping
    public ResponseEntity<ResponseObject> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.addProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("ok", "Product created successfully", createdProduct));
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage(), e);  // Log khi có lỗi tạo sản phẩm
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to create product: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Update an existing product", description = "Update a product by its ID")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 nếu không tìm thấy sản phẩm
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Trả về 400 nếu có lỗi về danh mục
        } catch (Exception e) {
            log.error("Error updating product with ID {}: {}", id, e.getMessage(), e);  // Log khi có lỗi cập nhật sản phẩm
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Trả về 500 nếu có lỗi hệ thống
        }
    }

    @Operation(summary = "Delete a product", description = "Delete a product by ID")
    @ApiResponse(responseCode = "200", description = "Product status set to 0 successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Failed to update product status")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        try {
            boolean isDeleted = productService.deleteProduct(id);

            if (!isDeleted) {
                log.warn("Product with ID {} not found", id);  // Log khi không tìm thấy sản phẩm
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("error", "Product with ID " + id + " not found", null));
            }

            log.info("Product with ID {} status set to 0", id);  // Log khi xóa sản phẩm thành công
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("success", "Product status set to 0", null));

        } catch (JwtException ex) {
            log.error("JWT error: {}", ex.getMessage(), ex);  // Log lỗi JWT
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("error", "JWT error: " + ex.getMessage(), null));
        } catch (Exception e) {
            log.error("Error deleting product with ID {}: {}", id, e.getMessage(), e);  // Log lỗi khi xóa sản phẩm
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Failed to update product status: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Search products by name", description = "Search for products by name")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @GetMapping("/search")
    public Page<ProductDTO> searchProductsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.searchProductsByName(name, page, size);
    }

    @Operation(summary = "Filter products by price range", description = "Filter products based on price range")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
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

    @Operation(summary = "Filter products by category", description = "Filter products based on category")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
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

    @Operation(summary = "Filter products by category and price", description = "Filter products based on category and price range")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
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
