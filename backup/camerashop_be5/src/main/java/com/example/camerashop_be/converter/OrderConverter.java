package com.example.camerashop_be.converter;

import com.example.camerashop_be.dto.response.OrderResponse;
import com.example.camerashop_be.entity.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderConverter {
	private final ModelMapper modelMapper;

	public OrderResponse convertToResponse(Order entity) {
		return modelMapper.map(entity, OrderResponse.class);
	}

	public Page<OrderResponse> convertToResponse(Page<Order> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToResponse);
	}
}
