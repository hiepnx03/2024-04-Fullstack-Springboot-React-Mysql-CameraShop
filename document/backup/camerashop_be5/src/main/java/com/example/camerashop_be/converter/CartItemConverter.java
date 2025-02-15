package com.example.camerashop_be.converter;


import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.dto.request.CartItemRequest;
import com.example.camerashop_be.dto.response.CartItemResponse;
import com.example.camerashop_be.dto.response.ProductResponse;
import com.example.camerashop_be.entity.CartItem;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartItemConverter {
    private final ModelMapper modelMapper;

    public CartItemResponse convertToResponse(CartItem entity) {
        CartItemResponse cartItemResponse = modelMapper.map(entity, CartItemResponse.class);
        ProductResponse productResponse = modelMapper.map(entity.getProduct(), ProductResponse.class);
        ImageDTO imageDTO = modelMapper.map(entity.getProduct().getImages().get(0), ImageDTO.class);
        productResponse.setImage(imageDTO);
        cartItemResponse.setProduct(productResponse);
        return cartItemResponse;
    }

    public CartItemResponse convertToResponse(CartItemRequest entity) {
        return modelMapper.map(entity, CartItemResponse.class);
    }

    public CartItem convertToEntity(CartItemRequest dto) {
        return modelMapper.map(dto, CartItem.class);
    }

    public CartItem convertToEntity(CartItemResponse dto) {
        return modelMapper.map(dto, CartItem.class);
    }

    public Page<CartItemResponse> convertToResponse(Page<CartItem> pageEntity) {
        if (pageEntity == null) {
            return null;
        }
        return pageEntity.map(this::convertToResponse);
    }
}
