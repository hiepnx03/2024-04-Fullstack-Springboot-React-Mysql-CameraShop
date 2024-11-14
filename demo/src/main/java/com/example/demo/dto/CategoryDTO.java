package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private boolean active;
    private boolean deleted;
    private boolean editable;
    private boolean visible;

    private Set<Long> productIds = new HashSet<>(); // Holds product names to avoid null
}
