package com.example.camerashop_be.controller;

import com.example.camerashop_be.dto.CartItemDTO;
import com.example.camerashop_be.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody CartItemDTO cartItemDTO) {
        return ResponseEntity.ok(cartItemService.createCartItem(cartItemDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable int id) {
        return ResponseEntity.ok(cartItemService.getCartItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.getAllCartItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable int id, @RequestBody CartItemDTO cartItemDTO) {
        return ResponseEntity.ok(cartItemService.updateCartItem(id, cartItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable int id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }
}
