package com.example.demo.dto;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private Product product;
    private Long userId;
}
