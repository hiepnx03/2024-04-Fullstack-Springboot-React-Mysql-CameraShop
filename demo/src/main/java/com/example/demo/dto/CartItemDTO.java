package com.example.demo.dto;

import com.example.demo.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDTO {
    private Long id;

    @NotNull(message = "Quantity cannot be null.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity; // Số lượng phải lớn hơn hoặc bằng 1

    @NotNull(message = "Product cannot be null.")
    private Product product; // Sản phẩm không được null

    @NotNull(message = "User ID cannot be null.")
    private Long userId; // User ID không được null
}
