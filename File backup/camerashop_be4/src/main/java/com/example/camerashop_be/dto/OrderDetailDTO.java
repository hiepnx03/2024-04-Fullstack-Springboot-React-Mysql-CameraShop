package com.example.camerashop_be.dto;

import com.example.camerashop_be.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
	private Long id;
	private Long price;
	private Integer quantity;
	private Integer discount;
	private ProductResponse product;
	private Long billId;
}
