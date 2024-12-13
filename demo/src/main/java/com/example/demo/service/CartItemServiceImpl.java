package com.example.demo.service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.CartItem;
import com.example.demo.converter.CartItemConverter;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemConverter cartItemConverter;

    @Override
    public CartItemDTO addCartItem(CartItemDTO cartItemDTO) {
        // Convert DTO to Entity
        CartItem cartItem = cartItemConverter.convertToEntity(cartItemDTO);
        // Save the CartItem entity
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        // Convert the saved entity back to DTO and return
        return cartItemConverter.convertToDTO(savedCartItem);
    }

    @Override
    public CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO) {
        Optional<CartItem> existingCartItem = cartItemRepository.findById(id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItemDTO.getQuantity());
            CartItem updatedCartItem = cartItemRepository.save(cartItem);
            return cartItemConverter.convertToDTO(updatedCartItem);
        }
        return null; // Có thể trả về null hoặc ném ngoại lệ nếu không tìm thấy
    }

    @Override
    public List<CartItemDTO> getAllCartItems(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream()
                .map(cartItemConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemDTO getCartItemById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return cartItem.map(cartItemConverter::convertToDTO).orElse(null);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
