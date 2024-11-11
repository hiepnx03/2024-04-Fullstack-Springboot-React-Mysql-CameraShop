package com.example.camerashop_be.controller;


import com.example.camerashop_be.dto.response.ProductResponse;
import com.example.camerashop_be.entity.EStatus;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.service.impl.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    //	@GetMapping("/all")
//	public ResponseEntity<ResponseObject> getAllByCategoryIdAndPrice(@RequestParam(name = "categoryId", required = false) long categoryId,
//																																	 @RequestParam(name = "price", required = false) long price,
//																																	 @RequestParam(name = "page", defaultValue = "0") int page,
//																																	 @RequestParam(name = "size", defaultValue = "10") int size) {
//		try {
//			Page<ProductResponse> products;
//			if (categoryId == 0) {
//				products = productService.getAll(price, EStatus.ACTIVE.getName(), page, size);
//			} else {
//				products = productService.getAllByCategoryId(categoryId, price, EStatus.ACTIVE.getName(), page, size);
//			}
//
//			return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", products));
//
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
//
//		}
//	}
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllByCategorySlugAndPrice(@RequestParam(name = "categorySlug", required = false) String slug,
                                                                       @RequestParam(name = "price", required = false) long price,
                                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<ProductResponse> products;
            if (slug.equals("null") || slug.equals("all")) {
                products = productService.getAll(price, EStatus.ACTIVE.getName(), page, size);
            } else {
                products = productService.getAllByCategorySlug(slug, price, EStatus.ACTIVE.getName(), page, size);
            }
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", products));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> getAllByCategoryIdAndPrice(@RequestParam(name = "key", required = false) String key,
                                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<ProductResponse> products = productService.search(key, page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Search with key " + key + " product successful!", products));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable(name = "id") Long id) {
        try {
            ProductResponse product = productService.getById(id);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get product" + id + "successfully!", product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getBySlug(@RequestParam(name = "slug") String slug) {
        try {
            ProductResponse product = productService.getBySlug(slug);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get product" + slug + "successfully!", product));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
