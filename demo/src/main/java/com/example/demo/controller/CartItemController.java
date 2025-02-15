package com.example.demo.controller;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {

    private final CartItemService cartItemService;

    @Operation(summary = "Add a cart item", description = "Adds an item to the cart")
    @ApiResponse(responseCode = "200", description = "Cart item added successfully")
    @ApiResponse(responseCode = "500", description = "Error occurred while adding cart item")
    @PostMapping
    public ResponseEntity<ResponseObject> addCartItem(@RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItemDTO savedCartItem = cartItemService.addCartItem(cartItemDTO);
            return ResponseEntity.ok(new ResponseObject("200", "Cart item added successfully", savedCartItem));
        } catch (Exception e) {
            log.error("Error occurred while adding cart item", e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while adding cart item", null));
        }
    }

    @Operation(summary = "Update a cart item", description = "Updates an existing cart item by ID")
    @ApiResponse(responseCode = "200", description = "Cart item updated successfully")
    @ApiResponse(responseCode = "404", description = "Cart item not found")
    @ApiResponse(responseCode = "500", description = "Error occurred while updating cart item")
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
            log.error("Error occurred while updating cart item", e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while updating cart item", null));
        }
    }

    @Operation(summary = "Get all cart items", description = "Retrieves all cart items for a specific user")
    @ApiResponse(responseCode = "200", description = "Cart items retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Error occurred while retrieving cart items")
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getAllCartItems(@PathVariable Long userId) {
        try {
            List<CartItemDTO> cartItems = cartItemService.getAllCartItems(userId);
            return ResponseEntity.ok(new ResponseObject("200", "Cart items retrieved successfully", cartItems));
        } catch (Exception e) {
            log.error("Error occurred while retrieving cart items", e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while retrieving cart items", null));
        }
    }

    @Operation(summary = "Get a cart item by ID", description = "Retrieves a single cart item by its ID")
    @ApiResponse(responseCode = "200", description = "Cart item retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Cart item not found")
    @ApiResponse(responseCode = "500", description = "Error occurred while retrieving cart item")
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
            log.error("Error occurred while retrieving cart item", e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while retrieving cart item", null));
        }
    }

    @Operation(summary = "Delete a cart item", description = "Deletes a cart item by its ID")
    @ApiResponse(responseCode = "200", description = "Cart item deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error occurred while deleting cart item")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCartItem(@PathVariable Long id) {
        try {
            cartItemService.deleteCartItem(id);
            return ResponseEntity.ok(new ResponseObject("200", "Cart item deleted successfully", null));
        } catch (Exception e) {
            log.error("Error occurred while deleting cart item", e);
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error occurred while deleting cart item", null));
        }
    }
}