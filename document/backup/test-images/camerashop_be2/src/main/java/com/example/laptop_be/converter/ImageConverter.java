package com.example.laptop_be.converter;


import com.example.laptop_be.dto.ImageDTO;
import com.example.laptop_be.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {

    public ImageDTO toDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        return dto;
    }

    public Image toEntity(ImageDTO dto) {
        Image image = new Image();
        image.setId(dto.getId());
        image.setUrl(dto.getUrl());
        return image;
    }
}
