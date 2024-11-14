package com.example.demo.converter;

import com.example.demo.dto.ImageDTO;
import com.example.demo.entity.Image;

public class ImageConverter {

    public static ImageDTO toDTO(Image image) {
        if (image == null) {
            return null;
        }
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        dto.setOrder(image.getImageOrder());
        return dto;
    }

    public static Image toEntity(ImageDTO dto) {
        if (dto == null) {
            return null;
        }
        Image image = new Image();
        image.setId(dto.getId());
        image.setUrl(dto.getUrl());
        image.setImageOrder(dto.getOrder());
        return image;
    }
}
