package com.example.demo.controller;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<ResponseObject> addCartItem(@RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItemDTO savedCartItem = cartItemService.addCartItem(cartItemDTO);
            return ResponseEntity.ok(new ResponseObject("200", "Cart item added successfully", savedCartItem));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while adding cart item", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCartItem(@PathVariable Long id, @RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItemDTO updatedCartItem = cartItemService.updateCartItem(id, cartItemDTO);
            if (updatedCartItem != null) {
                return ResponseEntity.ok(new ResponseObject("200", "Cart item updated successfully", updatedCartItem));
            } else {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Cart item not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while updating cart item", null));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getAllCartItems(@PathVariable Long userId) {
        try {
            List<CartItemDTO> cartItems = cartItemService.getAllCartItems(userId);
            return ResponseEntity.ok(new ResponseObject("200", "Cart items retrieved successfully", cartItems));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while retrieving cart items", null));
        }
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<ResponseObject> getCartItemById(@PathVariable Long id) {
        try {
            CartItemDTO cartItem = cartItemService.getCartItemById(id);
            if (cartItem != null) {
                return ResponseEntity.ok(new ResponseObject("200", "Cart item retrieved successfully", cartItem));
            } else {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Cart item not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while retrieving cart item", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCartItem(@PathVariable Long id) {
        try {
            cartItemService.deleteCartItem(id);
            return ResponseEntity.ok(new ResponseObject("200", "Cart item deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while deleting cart item", null));
        }
    }
}
