package com.example.camerashop_be.service;

import com.example.camerashop_be.dto.request.CartItemRequest;
import com.example.camerashop_be.dto.response.CartItemResponse;
import org.springframework.data.domain.Page;

public interface ICartService {
    Page<CartItemResponse> getByUserId(Long userId, Integer page, Integer size);

    CartItemResponse add(CartItemRequest cartItemRequest);

    CartItemResponse update(CartItemRequest cartItemRequest);

    void delete(Long id);

    void deleteByUserId(Long userId);

}
