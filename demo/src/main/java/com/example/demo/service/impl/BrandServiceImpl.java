package com.example.demo.service.impl;

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

    // Chuyển đổi Brand sang BrandDTO
    private BrandDTO convertToDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        brandDTO.setDescription(brand.getDescription());
        brandDTO.setLogo(brand.getLogo());
        brandDTO.setWebsite(brand.getWebsite());
        brandDTO.setActive(brand.isActive());
        return brandDTO;
    }

    // Chuyển đổi BrandDTO sang Brand
    private Brand convertToEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        brand.setLogo(brandDTO.getLogo());
        brand.setWebsite(brandDTO.getWebsite());
        brand.setActive(brandDTO.isActive());
        return brand;
    }

    @Override
    public BrandDTO addBrand(BrandDTO brandDTO) {
        // Chuyển BrandDTO thành Brand
        Brand brand = convertToEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);

        // Trả về BrandDTO sau khi lưu
        return convertToDTO(savedBrand);
    }

    @Override
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(brandId).orElse(null);
        if (existingBrand == null) {
            return null;  // Nếu không tìm thấy, trả về null
        }

        // Cập nhật các thông tin brand từ brandDTO
        existingBrand.setName(brandDTO.getName());
        existingBrand.setDescription(brandDTO.getDescription());
        existingBrand.setLogo(brandDTO.getLogo());
        existingBrand.setWebsite(brandDTO.getWebsite());
        existingBrand.setActive(brandDTO.isActive());

        // Lưu và trả về BrandDTO
        Brand updatedBrand = brandRepository.save(existingBrand);
        return convertToDTO(updatedBrand);
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(this::convertToDTO)  // Chuyển đổi từ Brand thành BrandDTO
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO getBrandById(Long brandId) {
        Brand existingBrand = brandRepository.findById(brandId).orElse(null);
        if (existingBrand == null) {
            return null;  // Trả về null nếu không tìm thấy
        }
        return convertToDTO(existingBrand);  // Chuyển đổi Brand thành BrandDTO
    }

    @Override
    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }

    @Override
    public BrandDTO findBrandByName(String name) {
        Brand existingBrand = brandRepository.findByName(name);
        if (existingBrand == null) {
            return null;  // Trả về null nếu không tìm thấy
        }
        return convertToDTO(existingBrand);  // Chuyển đổi Brand thành BrandDTO
    }
}
