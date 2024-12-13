package com.example.demo.service;

import com.example.demo.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {
    CartItemDTO addCartItem(CartItemDTO cartItemDTO);
    CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO);
    List<CartItemDTO> getAllCartItems(Long userId);
    CartItemDTO getCartItemById(Long id);
    void deleteCartItem(Long id);
}
