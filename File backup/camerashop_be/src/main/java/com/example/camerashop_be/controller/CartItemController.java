package com.example.camerashop_be.controller;

import com.example.camerashop_be.dto.request.CartItemRequest;
import com.example.camerashop_be.dto.response.CartItemResponse;
import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.service.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    private final ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAllByUserId(@PathVariable("id") Long id,
                                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<CartItemResponse> products = cartService.getByUserId(id, page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get all cart item with user id: " + id + " successful!", products));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> add(@RequestBody CartItemRequest cartItemRequest) {
        try {
            System.out.println("cao add");
            CartItemResponse cartItemResponse = cartService.add(cartItemRequest);
            System.out.println(cartItemResponse.getId() + "  id");
            return ResponseEntity.ok().body(new ResponseObject("ok", "Add prodcuct to cart successful!", cartItemResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseObject> update(@RequestBody CartItemRequest cartItemRequest) {
        try {
            CartItemResponse cartItemResponse = cartService.update(cartItemRequest);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Updated!", cartItemResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable(name = "id") Long id) {
        try {
            cartService.delete(id);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Deleted!", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<ResponseObject> deleteByUserId(@PathVariable(name = "userId") Long userId) {
        try {
            cartService.deleteByUserId(userId);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Deleted!", ""));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
