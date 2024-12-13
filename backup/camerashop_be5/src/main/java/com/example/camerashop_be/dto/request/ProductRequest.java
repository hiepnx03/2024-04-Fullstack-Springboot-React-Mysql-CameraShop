package com.example.camerashop_be.dto.request;

import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.dto.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {
	private Long id;
	private String name;
	private Long price;
	private String description;
	private Integer discount;
	private Integer quantity;
	private Integer status;
	private String slug;
	private CategoryResponse category;
	private List<ImageDTO> images;
}
