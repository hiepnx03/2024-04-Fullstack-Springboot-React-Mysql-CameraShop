package com.example.camerashop_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private int idCategory;
    private String categoryName;
    private List<ProductDTO> productList;
}
