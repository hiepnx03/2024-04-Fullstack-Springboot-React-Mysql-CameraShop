package com.example.camerashop_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
	private Long id;
	private String name;
	private Integer status;
	private String slug;
}