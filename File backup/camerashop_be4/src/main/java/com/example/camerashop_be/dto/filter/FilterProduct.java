package com.example.camerashop_be.dto.filter;

import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterProduct {
	private Integer status;
	private String name;
	private Long price;
	private Integer categoryId;
	private List<Date> createdAt;
	private Integer page;
	private Integer size;


}
