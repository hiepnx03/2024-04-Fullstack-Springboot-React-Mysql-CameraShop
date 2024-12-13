package com.example.laptop_be.service;

import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.CartItemRepository;
import com.example.laptop_be.dto.CartItemDTO;
import com.example.laptop_be.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private Converter converter;

    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = converter.convertToEntity(cartItemDTO);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return converter.convertToDto(savedCartItem);
    }

    public CartItemDTO getCartItemById(int id) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        return converter.convertToDto(cartItem);
    }

    public List<CartItemDTO> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public CartItemDTO updateCartItem(int id, CartItemDTO cartItemDTO) {
        CartItem cartItem = converter.convertToEntity(cartItemDTO);
        cartItem.setIdCart(id);
        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        return converter.convertToDto(updatedCartItem);
    }

    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
    }
}