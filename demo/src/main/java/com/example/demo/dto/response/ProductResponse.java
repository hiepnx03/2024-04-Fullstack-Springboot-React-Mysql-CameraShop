package com.example.demo.dto.response;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private Double sellPrice;
    private String description;
    private Integer discount;
    private Integer quantity;
    private Integer status;
    private String slug;

    private CategoryDTO category;

    private ImageDTO image;
}
