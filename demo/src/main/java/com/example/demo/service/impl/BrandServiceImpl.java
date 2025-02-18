package com.example.demo.service.impl;

import com.example.demo.converter.BrandConverter;
import com.example.demo.dto.BrandDTO;
import com.example.demo.entity.Brand;
import com.example.demo.repository.BrandRepository;
import com.example.demo.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandConverter brandConverter;



    @Override
    public BrandDTO addBrand(BrandDTO brandDTO) {
        Brand brand = brandConverter.toEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);
        return brandConverter.toDTO(savedBrand);
    }

    @Override
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(brandId).orElse(null);
        if (existingBrand == null) {
            return null;
        }

        existingBrand.setName(brandDTO.getName());
        existingBrand.setDescription(brandDTO.getDescription());
        existingBrand.setLogo(brandDTO.getLogo());
        existingBrand.setWebsite(brandDTO.getWebsite());
        existingBrand.setActive(brandDTO.isActive());

        Brand updatedBrand = brandRepository.save(existingBrand);
        return brandConverter.toDTO(updatedBrand);
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brandConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO getBrandById(Long brandId) {
        Brand existingBrand = brandRepository.findById(brandId).orElse(null);
        if (existingBrand == null) {
            return null;
        }
        return brandConverter.toDTO(existingBrand);
    }

    @Override
    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }

    @Override
    public BrandDTO findBrandByName(String name) {
        Brand existingBrand = brandRepository.findByName(name);
        if (existingBrand == null) {
            return null;
        }
        return brandConverter.toDTO(existingBrand);
    }
}
