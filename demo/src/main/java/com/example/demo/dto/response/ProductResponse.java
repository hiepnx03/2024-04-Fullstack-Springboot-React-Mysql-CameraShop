package com.example.demo.dto.response;

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
    private Long price;
    private String description;
    private Integer discount;
    private Integer quantity;
    private Integer status;
    private String slug;

    private CategoryResponse category;
    private ImageDTO image;
}
