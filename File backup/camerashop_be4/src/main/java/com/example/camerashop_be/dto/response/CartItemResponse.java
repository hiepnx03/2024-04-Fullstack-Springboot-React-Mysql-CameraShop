package com.example.camerashop_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
	private Long id;
	private Integer quantity;
	private ProductResponse product;
	private Long userId;
}
