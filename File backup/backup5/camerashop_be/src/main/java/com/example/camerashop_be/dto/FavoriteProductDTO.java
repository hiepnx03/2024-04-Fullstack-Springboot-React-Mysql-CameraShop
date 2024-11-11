package com.example.camerashop_be.dto;

import lombok.Data;

@Data
public class FavoriteProductDTO {
    private int idFavoriteBook;
    private ProductDTO product;
    private UserDTO user;
}
