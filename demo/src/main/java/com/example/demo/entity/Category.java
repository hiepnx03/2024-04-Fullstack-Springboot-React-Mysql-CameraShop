package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Category extends Base<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;
    private boolean active;
    private String category;
    private boolean deleted;
    private boolean editable;
    private boolean visible;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>(); // Danh sách sản phẩm thuộc danh mục này
}
