package com.example.laptop_be.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class UserDTO {
    private int idUser;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private char gender;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private String deliveryAddress;
    private String avatar;
    private boolean enabled;
    private String activationCode;
    private List<ReviewDTO> listReviews;
    private List<FavoriteProductDTO> listFavoriteBooks;
    private List<RoleDTO> listRoles;
    private List<OrderDTO> listOrders;
    private List<CartItemDTO> listCartItems;
    private List<FeedbacksDTO> listFeedbacks;
}
