package com.example.camerashop_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO implements Serializable {
	private Integer id;
	private String link;
	private Long productId;
}
