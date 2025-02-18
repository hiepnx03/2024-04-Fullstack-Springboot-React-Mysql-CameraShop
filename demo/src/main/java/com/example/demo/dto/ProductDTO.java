package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống.")
    @Size(min = 3, max = 255, message = "Tên sản phẩm phải có ít nhất 3 ký tự và tối đa 255 ký tự.")
    private String name;

    @Size(max = 1000, message = "Mô tả sản phẩm không được dài quá 1000 ký tự.")
    private String description;

    @NotNull(message = "Giá nhập không được để trống.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá nhập phải lớn hơn hoặc bằng 0.")
    private Double importPrice;

    @NotNull(message = "Giá bán không được để trống.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá bán phải lớn hơn hoặc bằng 0.")
    private Double listPrice;

    @NotNull(message = "Giá bán phải không được để trống.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá bán phải lớn hơn hoặc bằng 0.")
    private Double sellPrice;

    @NotNull(message = "Số lượng sản phẩm không được để trống.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Số lượng phải lớn hơn hoặc bằng 0.")
    private Double quantity;

    @NotNull(message = "Số lượng đã bán không được để trống.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Số lượng đã bán phải lớn hơn hoặc bằng 0.")
    private Double soldQuantity;

    private Integer status;

    @NotEmpty(message = "Danh sách ID danh mục không được để trống.")
    private Set<Long> categoryIds; // Danh sách ID của các danh mục

    @NotEmpty(message = "Danh sách hình ảnh không được để trống.")
    private Set<ImageDTO> images; // Danh sách hình ảnh

    @NotNull(message = "Brand ID cannot be null.")
    private Long brandId;

    // Constructors, getters, and setters (lombok)
}
