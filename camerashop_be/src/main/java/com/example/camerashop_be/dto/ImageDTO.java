package com.example.camerashop_be.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private int idImage;
    private String nameImage;
    private boolean isThumbnail;
    private String urlImage;
    private String dataImage;
    private ProductDTO product;
}