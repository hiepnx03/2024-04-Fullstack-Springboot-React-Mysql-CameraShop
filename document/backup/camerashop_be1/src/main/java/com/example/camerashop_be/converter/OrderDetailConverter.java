package com.example.camerashop_be.converter;


import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.dto.OrderDetailDTO;
import com.example.camerashop_be.dto.request.OrderDetailRequest;
import com.example.camerashop_be.entity.OrderDetail;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class OrderDetailConverter {
	private final ModelMapper modelMapper;

	public OrderDetail convertToEntity(OrderDetailRequest orderDetailDTO) {

		return modelMapper.map(orderDetailDTO, OrderDetail.class);
	}

	public OrderDetailDTO convertToResponse(OrderDetail orderDetail) {
		ImageDTO imageDTO = modelMapper.map(orderDetail.getProduct().getImages().get(0), ImageDTO.class);
		OrderDetailDTO result = modelMapper.map(orderDetail, OrderDetailDTO.class);
		result.getProduct().setImage(imageDTO);
		return result;
	}
}
