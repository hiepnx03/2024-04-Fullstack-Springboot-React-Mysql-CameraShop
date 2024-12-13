package com.example.laptop_be.controller;

import com.example.laptop_be.dto.FavoriteProductDTO;
import com.example.laptop_be.service.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoriteProducts")
public class FavoriteProductController {

    @Autowired
    private FavoriteProductService favoriteProductService;

    @PostMapping
    public ResponseEntity<FavoriteProductDTO> createFavoriteProduct(@RequestBody FavoriteProductDTO favoriteProductDTO) {
        return ResponseEntity.ok(favoriteProductService.createFavoriteProduct(favoriteProductDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteProductDTO> getFavoriteProductById(@PathVariable int id) {
        return ResponseEntity.ok(favoriteProductService.getFavoriteProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<FavoriteProductDTO>> getAllFavoriteProducts() {
        return ResponseEntity.ok(favoriteProductService.getAllFavoriteProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoriteProductDTO> updateFavoriteProduct(@PathVariable int id, @RequestBody FavoriteProductDTO favoriteProductDTO) {
        return ResponseEntity.ok(favoriteProductService.updateFavoriteProduct(id, favoriteProductDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoriteProduct(@PathVariable int id) {
        favoriteProductService.deleteFavoriteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
