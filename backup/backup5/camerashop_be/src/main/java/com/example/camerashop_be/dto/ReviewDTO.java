package com.example.camerashop_be.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDTO {
    private long idReview;
    private String content;
    private float ratingPoint;
    private Timestamp timestamp;
    private ProductDTO product;
    private UserDTO user;
    private OrderDetailDTO orderDetail;
}