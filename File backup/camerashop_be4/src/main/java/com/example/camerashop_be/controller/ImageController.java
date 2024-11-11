package com.example.camerashop_be.controller;


import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.service.impl.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getByProductId(@RequestParam(name = "productId", required = false) long productId
    ) {
        try {
            ImageDTO image = imageService.getByProductId(productId);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get image successful!", image));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseObject> getAllByProductId(@PathVariable(name = "productId") long productId
    ) {
        try {
            List<ImageDTO> image = imageService.getAllByProductId(productId);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get image successful!", image));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> getAllByProductId(@RequestBody List<ImageDTO> imageDTOs
    ) {
        try {
            List<ImageDTO> imagesResult = imageService.add(imageDTOs);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Add images successful!", imagesResult));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
