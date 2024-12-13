package com.example.demo.converter;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.CartItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {
    private final ModelMapper modelMapper;

    public CartItemConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public CartItemDTO convertToDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

    public CartItem convertToEntity(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }
}
