package com.example.laptop_be.controller;

import com.example.laptop_be.dto.ImageDTO;
import com.example.laptop_be.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {
        return ResponseEntity.ok(imageService.createImage(imageDTO));
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.uploadImage(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable int id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable int id, @RequestBody ImageDTO imageDTO) {
        return ResponseEntity.ok(imageService.updateImage(id, imageDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable int id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
