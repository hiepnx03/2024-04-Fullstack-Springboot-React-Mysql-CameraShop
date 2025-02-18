package com.example.demo.converter;

import com.example.demo.dto.BrandDTO;
import com.example.demo.entity.Brand;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter {
    private final ModelMapper modelMapper;

    public BrandConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BrandDTO toDTO(Brand brand) {
        return modelMapper.map(brand, BrandDTO.class);
    }

    public Brand toEntity(BrandDTO brandDTO) {
        return modelMapper.map(brandDTO, Brand.class);
    }
}

