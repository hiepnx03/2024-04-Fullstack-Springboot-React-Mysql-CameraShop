package com.example.camerashop_be.service.impl;


import com.example.camerashop_be.converter.ImageConverter;
import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.entity.Image;
import com.example.camerashop_be.repository.ImageRepo;
import com.example.camerashop_be.service.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageService implements IImageService {
    private final ImageConverter imageConverter;
    private final ImageRepo imageRepo;

    @Override
    public List<ImageDTO> getAllByProductId(Long id) {
        return imageRepo.findAllByProductId(id).stream().map(entity -> imageConverter.convertToDto(entity)).collect(Collectors.toList());
    }

    @Override
    public ImageDTO getByProductId(Long id) {
        return imageConverter.convertToDto(imageRepo.findTopByProductId(id));
    }

    @Override
    public ImageDTO add(ImageDTO imageDTO) {
        Image image = imageConverter.convertToEntity(imageDTO);
        return imageConverter.convertToDto(imageRepo.save(image));
    }

    @Override
    public List<ImageDTO> add(List<ImageDTO> imageDTOs) {
        List<Image> images = imageDTOs.stream().map(e -> imageConverter.convertToEntity(e)).collect(Collectors.toList());
        List<Image> imagesAdded = imageRepo.saveAll(images);
        return imagesAdded.stream().map(e -> imageConverter.convertToDto(e)).collect(Collectors.toList());
    }

    @Override
    public Integer getSizeByProductId(Long productId) {
        try {
            Integer dd = imageRepo.getSizeByProductId(productId);
            return imageRepo.getSizeByProductId(productId);
        } catch (Exception e) {
            return null;
        }
    }
}
