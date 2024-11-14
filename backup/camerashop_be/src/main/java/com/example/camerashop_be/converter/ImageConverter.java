package com.example.camerashop_be.converter;

import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.entity.Image;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ImageConverter {
	private final ModelMapper modelMapper;

	public ImageDTO convertToDto(Image entity) {
		return modelMapper.map(entity, ImageDTO.class);
	}

	public Image convertToEntity(ImageDTO dto) {
		return modelMapper.map(dto, Image.class);
	}
}
