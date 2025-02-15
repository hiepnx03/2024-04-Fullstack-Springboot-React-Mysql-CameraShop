package com.example.camerashop_be.service;


import com.example.camerashop_be.dto.ImageDTO;

import java.util.List;

public interface IImageService {
    List<ImageDTO> getAllByProductId(Long id);

    ImageDTO getByProductId(Long id);

    ImageDTO add(ImageDTO imageDTO);

    List<ImageDTO> add(List<ImageDTO> imageDTOs);

    Integer getSizeByProductId(Long productId);
}
