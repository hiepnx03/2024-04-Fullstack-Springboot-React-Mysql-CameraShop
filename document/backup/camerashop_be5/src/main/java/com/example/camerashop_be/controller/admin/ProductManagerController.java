package com.example.camerashop_be.controller.admin;


import com.example.camerashop_be.dto.BestSellingProduct;
import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.dto.filter.FilterProduct;
import com.example.camerashop_be.dto.request.ProductRequest;
import com.example.camerashop_be.dto.response.ProductResponse;
import com.example.camerashop_be.entity.Filter;
import com.example.camerashop_be.entity.QueryOperator;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.repository.specs.ProductSpecification;
import com.example.camerashop_be.service.IImageService;
import com.example.camerashop_be.service.IProductService;
import com.example.camerashop_be.service.IStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/product")
public class ProductManagerController {
    private final int MAX_NUMBER_IMAGE = 5;
    private final IProductService productService;
    private final IStorageService storageService;
    private final IImageService imageService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllByCategoryIdAndPrice(@RequestParam(name = "categoryId", required = false) long categoryId,
                                                                     @RequestParam(name = "price", required = false) long price,
                                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<ProductResponse> products;
            if (categoryId == 0) {
                products = productService.getAll(price, null, page, size);
            } else {
                products = productService.getAllByCategoryId(categoryId, price, null, page, size);
            }
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", products));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> add(@RequestPart("product") String productRequest, @RequestPart(value = "file", required = false) MultipartFile[] file) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductRequest product = objectMapper.readValue(productRequest, ProductRequest.class);

            if (product.getId() == null && productService.getBySlug(product.getSlug()) != null) {
                throw new RuntimeException("Slug already exist!");
            }
            ProductResponse productResponse;
            if (product.getId() != null) {
                productResponse = productService.edit(product);
            } else {
                productResponse = productService.add(product);
            }

            if (file != null && file.length > 0) {
                List<String> listImage = new ArrayList<>();
                for (MultipartFile multipartFile : file) {
                    String generatedFileName = storageService.storeFile(multipartFile);
                    listImage.add(generatedFileName);
                }
                if (imageService.getSizeByProductId(product.getId()) != null && imageService.getSizeByProductId(product.getId()) + file.length > MAX_NUMBER_IMAGE) {
                    throw new RuntimeException("The allowed number of photos has been exceeded (maximum 5 photos)!");
                }
                List<ImageDTO> imageDTOs = imageService.add(listImage.stream().map(e -> new ImageDTO(null, e, productResponse.getId())).collect(Collectors.toList()));
//				imageService.add(imageDTOs);
            }

//			ProductResponse result = productService.getById(productResponse.getId());
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", null));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseObject> filter(@RequestBody FilterProduct filterProduct) {
        try {
            ProductSpecification productSpecifications = new ProductSpecification();
            if (filterProduct.getCreatedAt() == null || filterProduct.getCreatedAt().size() < 1) {
            } else if (filterProduct.getCreatedAt().get(0) == null) {
                productSpecifications.add(new Filter("createdAt", QueryOperator.IN, filterProduct.getCreatedAt().get(1)));
            } else if (filterProduct.getCreatedAt().get(1) == null) {
                productSpecifications.add(new Filter("createdAt", QueryOperator.IN, filterProduct.getCreatedAt().get(0)));
            } else if (filterProduct.getCreatedAt().get(0) != null && filterProduct.getCreatedAt().get(1) != null) {
                productSpecifications.add(new Filter("createdAt", QueryOperator.IN, filterProduct.getCreatedAt()));
            }
            if (filterProduct.getName() != null) {
                productSpecifications.add(new Filter("name", QueryOperator.LIKE, filterProduct.getName()));
            }
            if (filterProduct.getStatus() != null) {
                productSpecifications.add(new Filter("status", QueryOperator.EQUAL, filterProduct.getStatus()));
            }
            if (filterProduct.getCategoryId() != null) {
                productSpecifications.add(new Filter("category", QueryOperator.EQUAL, filterProduct.getCategoryId()));
            }
            Page<ProductResponse> productResponses = productService.filter(productSpecifications, filterProduct.getPage(), filterProduct.getSize());
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Filter product successfully!", productResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/best-selling")
    public ResponseEntity<ResponseObject> getBestSellingCurrentMonth() {
        try {
            Page<BestSellingProduct> productResponses = productService.getBestSellingInMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get best-selling product successfully!", productResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("ok", e.getMessage(), ""));
        }
    }

    public static boolean isNumber(String text) {
        return text.chars().allMatch(Character::isDigit);
    }

    private String convertWithoutUnderStoke(String str) {
        return str.split("_")[0];
    }
}
