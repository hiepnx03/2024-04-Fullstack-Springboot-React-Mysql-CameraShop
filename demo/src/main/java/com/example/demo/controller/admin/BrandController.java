package com.example.demo.controller.admin;

import com.example.demo.dto.BrandDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ResponseObject> addBrand(@RequestBody BrandDTO brandDTO) {
        try {
            BrandDTO savedBrand = brandService.addBrand(brandDTO);
            return ResponseEntity.ok(new ResponseObject("200", "Brand created successfully", savedBrand));
        } catch (Exception e) {
            // Catch bất kỳ lỗi nào và trả về thông báo lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while creating the brand", null));
        }
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<ResponseObject> updateBrand(@PathVariable Long brandId, @RequestBody BrandDTO brandDTO) {
        try {
            BrandDTO updatedBrand = brandService.updateBrand(brandId, brandDTO);
            if (updatedBrand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("404", "Brand not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Brand updated successfully", updatedBrand));
        } catch (Exception e) {
            // Catch bất kỳ lỗi nào và trả về thông báo lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while updating the brand", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllBrands() {
        try {
            List<BrandDTO> brands = brandService.getAllBrands();
            return ResponseEntity.ok(new ResponseObject("200", "Brands retrieved successfully", brands));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while retrieving brands", null));
        }
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<ResponseObject> getBrandById(@PathVariable Long brandId) {
        try {
            BrandDTO brand = brandService.getBrandById(brandId);
            if (brand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("404", "Brand not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Brand retrieved successfully", brand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while retrieving the brand", null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> findBrandByName(@RequestParam String name) {
        try {
            BrandDTO brand = brandService.findBrandByName(name);
            if (brand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("404", "Brand not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Brand retrieved successfully", brand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while searching for the brand", null));
        }
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<ResponseObject> deleteBrand(@PathVariable Long brandId) {
        try {
            brandService.deleteBrand(brandId);
            return ResponseEntity.ok(new ResponseObject("200", "Brand deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("500", "An error occurred while deleting the brand", null));
        }
    }
}
