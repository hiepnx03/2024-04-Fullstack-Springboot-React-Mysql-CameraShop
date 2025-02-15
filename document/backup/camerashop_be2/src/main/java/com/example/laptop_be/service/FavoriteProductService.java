package com.example.laptop_be.service;

import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.FavoriteProductRepository;
import com.example.laptop_be.dto.FavoriteProductDTO;
import com.example.laptop_be.entity.FavoriteProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteProductService {
    @Autowired
    private FavoriteProductRepository favoriteProductRepository;

    @Autowired
    private Converter converter;

    public FavoriteProductDTO createFavoriteProduct(FavoriteProductDTO favoriteProductDTO) {
        FavoriteProduct favoriteProduct = converter.convertToEntity(favoriteProductDTO);
        FavoriteProduct savedFavoriteProduct = favoriteProductRepository.save(favoriteProduct);
        return converter.convertToDto(savedFavoriteProduct);
    }

    public FavoriteProductDTO getFavoriteProductById(int id) {
        FavoriteProduct favoriteProduct = favoriteProductRepository.findById(id).orElse(null);
        return converter.convertToDto(favoriteProduct);
    }

    public List<FavoriteProductDTO> getAllFavoriteProducts() {
        List<FavoriteProduct> favoriteProducts = favoriteProductRepository.findAll();
        return favoriteProducts.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public FavoriteProductDTO updateFavoriteProduct(int id, FavoriteProductDTO favoriteProductDTO) {
        FavoriteProduct favoriteProduct = converter.convertToEntity(favoriteProductDTO);
        favoriteProduct.setIdFavoriteBook(id);
        FavoriteProduct updatedFavoriteProduct = favoriteProductRepository.save(favoriteProduct);
        return converter.convertToDto(updatedFavoriteProduct);
    }

    public void deleteFavoriteProduct(int id) {
        favoriteProductRepository.deleteById(id);
    }
}
