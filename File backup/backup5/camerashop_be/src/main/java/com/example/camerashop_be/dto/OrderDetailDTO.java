package com.example.camerashop_be.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private long idOrderDetail;
    private int quantity;
    private double price;
    private boolean isReview;
    private ProductDTO product;
    private OrderDTO order;
}