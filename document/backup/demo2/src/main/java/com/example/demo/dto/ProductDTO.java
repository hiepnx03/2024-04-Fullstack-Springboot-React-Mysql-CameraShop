package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;

//    private Set<Long> categories = new HashSet<>();
//    private Set<ImageDTO> images = new HashSet<>();


    private Set<Long> categoryIds; // Danh sách ID của các danh mục
    private Set<ImageDTO> images; // Danh sách hình ảnh

    // Constructors, getters, and setters
}

