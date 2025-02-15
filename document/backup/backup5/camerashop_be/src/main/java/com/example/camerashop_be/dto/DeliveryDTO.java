package com.example.camerashop_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeliveryDTO {
    private int idDelivery;
    private String nameDelivery;
    private String description;
    private double feeDelivery;
    private List<OrderDTO> listOrders;
}