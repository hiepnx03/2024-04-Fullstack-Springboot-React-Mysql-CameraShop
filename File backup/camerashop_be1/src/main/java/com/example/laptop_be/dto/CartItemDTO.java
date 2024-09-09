package com.example.laptop_be.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private int idCart;
    private int quantity;
    private ProductDTO product;
    private UserDTO user;
}