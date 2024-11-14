package com.example.camerashop_be.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
	private String key;
	private QueryOperator operator;
	private Object value;
}