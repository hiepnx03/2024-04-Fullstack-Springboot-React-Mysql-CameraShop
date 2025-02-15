package com.example.demo.controller.admin;

import com.example.demo.dto.BrandDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
public class BrandManagerController {

    private final BrandService brandService;

    @Operation(summary = "Add a new brand", description = "Create a new brand in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand created successfully"),
            @ApiResponse(responseCode = "500", description = "An error occurred while creating the brand")
    })
    @PostMapping
    public ResponseEntity<ResponseObject> addBrand(@RequestBody BrandDTO brandDTO) {
        try {
            log.info("Adding new brand: {}", brandDTO);
            BrandDTO savedBrand = brandService.addBrand(brandDTO);
            return ResponseEntity.ok(new ResponseObject("200", "Brand created successfully", savedBrand));
        } catch (Exception e) {
            log.error("Error while creating the brand", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while creating the brand", null));
        }
    }

    @Operation(summary = "Update an existing brand", description = "Update the details of an existing brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while updating the brand")
    })
    @PutMapping("/{brandId}")
    public ResponseEntity<ResponseObject> updateBrand(@PathVariable Long brandId, @RequestBody BrandDTO brandDTO) {
        try {
            log.info("Updating brand with ID: {}", brandId);
            BrandDTO updatedBrand = brandService.updateBrand(brandId, brandDTO);
            if (updatedBrand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("404", "Brand not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Brand updated successfully", updatedBrand));
        } catch (Exception e) {
            log.error("Error while updating the brand with ID: {}", brandId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while updating the brand", null));
        }
    }

    @Operation(summary = "Get all brands", description = "Retrieve a list of all brands in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brands retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "An error occurred while retrieving the brands")
    })
    @GetMapping
    public ResponseEntity<ResponseObject> getAllBrands() {
        try {
            log.info("Fetching all brands");
            List<BrandDTO> brands = brandService.getAllBrands();
            return ResponseEntity.ok(new ResponseObject("200", "Brands retrieved successfully", brands));
        } catch (Exception e) {
            log.error("Error while retrieving brands", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while retrieving brands", null));
        }
    }

    @Operation(summary = "Get brand by ID", description = "Retrieve a brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while retrieving the brand")
    })
    @GetMapping("/{brandId}")
    public ResponseEntity<ResponseObject> getBrandById(@PathVariable Long brandId) {
        try {
            log.info("Fetching brand with ID: {}", brandId);
            BrandDTO brand = brandService.getBrandById(brandId);
            if (brand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("404", "Brand not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Brand retrieved successfully", brand));
        } catch (Exception e) {
            log.error("Error while retrieving the brand with ID: {}", brandId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while retrieving the brand", null));
        }
    }

    @Operation(summary = "Search for a brand by name", description = "Search for a brand by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while searching for the brand")
    })
    @GetMapping("/search")
    public ResponseEntity<ResponseObject> findBrandByName(@RequestParam String name) {
        try {
            log.info("Searching for brand by name: {}", name);
            BrandDTO brand = brandService.findBrandByName(name);
            if (brand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("404", "Brand not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Brand retrieved successfully", brand));
        } catch (Exception e) {
            log.error("Error while searching for the brand by name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while searching for the brand", null));
        }
    }

    @Operation(summary = "Delete a brand by ID", description = "Delete a brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "500", description = "An error occurred while deleting the brand")
    })
    @DeleteMapping("/{brandId}")
    public ResponseEntity<ResponseObject> deleteBrand(@PathVariable Long brandId) {
        try {
            log.info("Deleting brand with ID: {}", brandId);
            brandService.deleteBrand(brandId);
            return ResponseEntity.ok(new ResponseObject("200", "Brand deleted successfully", null));
        } catch (Exception e) {
            log.error("Error while deleting the brand with ID: {}", brandId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while deleting the brand", null));
        }
    }
}
