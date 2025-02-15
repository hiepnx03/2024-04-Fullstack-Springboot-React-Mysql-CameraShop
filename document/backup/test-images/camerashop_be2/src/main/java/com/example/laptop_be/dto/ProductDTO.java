package com.example.laptop_be.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
//    private List<ImageDTO> images;
private List<String> imageUrls;

    // getters and setters
}
