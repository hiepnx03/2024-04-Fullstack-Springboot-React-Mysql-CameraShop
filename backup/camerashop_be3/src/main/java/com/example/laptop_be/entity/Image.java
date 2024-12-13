package com.example.laptop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private int idImage;
    @Column(name = "name_image")
    private String nameImage;
    @Column(name = "is_thumbnail")
    private boolean isThumbnail;
    @Column(name = "url_image")
    private String urlImage;
    @Lob
    @Column(name = "data_image", columnDefinition = "LONGTEXT")
    private String dataImage;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
}