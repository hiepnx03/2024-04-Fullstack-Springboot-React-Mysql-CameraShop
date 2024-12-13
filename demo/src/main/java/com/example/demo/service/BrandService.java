package com.example.demo.service;

import com.example.demo.dto.BrandDTO;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    BrandDTO addBrand(BrandDTO brandDTO);
    BrandDTO updateBrand(Long brandId, BrandDTO brandDTO);
    List<BrandDTO> getAllBrands();
    BrandDTO getBrandById(Long brandId);
    void deleteBrand(Long brandId);
    BrandDTO findBrandByName(String name);
}
