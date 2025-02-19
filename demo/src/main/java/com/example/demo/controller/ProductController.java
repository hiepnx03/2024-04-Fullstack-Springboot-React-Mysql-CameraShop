package com.example.demo.controller;

import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.EStatus;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products by category slug and price",
            description = "Retrieve products filtered by category, price, and pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of products"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid parameters")
    })
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllByCategorySlugAndPrice(
            @RequestParam(name = "categorySlug", required = false) String slug,
            @RequestParam(name = "sellPrice", required = false) Double sellPrice,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<ProductResponse> products;

            // Log the request details
            log.info("Fetching products with slug: {}, price: {}, page: {}, size: {}", slug, sellPrice, page, size);

            if (slug == null || slug.equalsIgnoreCase("null") || slug.equalsIgnoreCase("all")) {
                products = productService.getAll(sellPrice, EStatus.ACTIVE.getName(), page, size);
            } else {
                products = productService.getAllByCategorySlug(slug, sellPrice, EStatus.ACTIVE.getName(), page, size);
            }

            return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", products));

        } catch (Exception e) {
            // Log the error
            log.error("Error fetching products", e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
