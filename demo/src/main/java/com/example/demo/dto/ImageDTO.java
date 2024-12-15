package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Long id;

    @NotBlank(message = "URL cannot be empty.")
    @Pattern(regexp = "^https?:\\/\\/(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(\\/.*)?$", message = "Invalid URL format.")
    private String url;  // URL của hình ảnh, không được rỗng và phải có định dạng URL hợp lệ.

    @NotNull(message = "Order cannot be null.")
    @Positive(message = "Order must be a positive number.")
    private Long order;  // Thứ tự hiển thị, phải là số dương và không được null.
}
