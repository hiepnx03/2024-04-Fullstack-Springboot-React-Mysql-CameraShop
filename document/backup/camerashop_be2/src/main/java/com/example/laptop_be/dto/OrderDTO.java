package com.example.laptop_be.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class OrderDTO {
    private int idOrder;
    private Date dateCreated;
    private String deliveryAddress;
    private String phoneNumber;
    private String fullName;
    private double totalPriceProduct;
    private double feeDelivery;
    private double feePayment;
    private double totalPrice;
    private String status;
    private String note;
    private List<OrderDetailDTO> listOrderDetails;
    private UserDTO user;
    private PaymentDTO payment;
    private DeliveryDTO delivery;
}