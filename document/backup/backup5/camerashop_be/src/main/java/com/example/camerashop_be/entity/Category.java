package com.example.camerashop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private int idCategory;
    @Column(name = "category_name")
    private String categoryName;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "id_category"), inverseJoinColumns = @JoinColumn(name = "id_product"))
    private List<Product> productList;
}