package com.example.camerashop_be.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private int idProduct;
    private String productName;
    private double listPrice;
    private double sellPrice;
    private String description;
    private int quantity;
    private double avgRating;
    private int soldQuantity;
    private int discountPercent;
//    private List<CategoryDTO> categoryList;
//    private List<ImageDTO> imageList;
    private List<Integer> categoryIds; // IDs of categories
    private List<Integer> imageIds; // IDs of images
    private List<String> imageUrls;  // New field

    private List<ReviewDTO> reviewList;
    private List<OrderDetailDTO> orderDetailList;
    private List<FavoriteProductDTO> favoriteProductList;
    private List<CartItemDTO> listCartItems;
}
