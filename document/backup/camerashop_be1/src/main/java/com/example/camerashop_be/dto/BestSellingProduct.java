package com.example.camerashop_be.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestSellingProduct {
	private Long id;
	private String name;
	private Long price;
	private Integer status;
	private Long quantity;

}
