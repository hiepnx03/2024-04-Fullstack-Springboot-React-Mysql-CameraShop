package com.example.laptop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "list_price")
    private double listPrice; // Giá niêm yết

    @Column(name = "sell_price")
    private double sellPrice; // Giá bán

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Constructors, getters, and setters
}
