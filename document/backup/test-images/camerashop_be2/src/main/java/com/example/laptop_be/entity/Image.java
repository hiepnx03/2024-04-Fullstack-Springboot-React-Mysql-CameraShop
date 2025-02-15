package com.example.laptop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // getters and setters
}