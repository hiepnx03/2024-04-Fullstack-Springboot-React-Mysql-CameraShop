package com.example.demo.dto;

import com.example.demo.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String url;   // URL của hình ảnh
    private Long order;   // Thứ tự hiển thị

//    private Long productId;
}