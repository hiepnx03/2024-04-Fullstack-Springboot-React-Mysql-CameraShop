package com.example.camerashop_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
	private Long id;
	private Long price;
	private Integer quantity;
	private Integer discount;
	private Long productId;
	private Long billId;
}
