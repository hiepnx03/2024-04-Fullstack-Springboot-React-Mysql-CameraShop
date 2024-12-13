package com.example.laptop_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentDTO {
    private int idPayment;
    private String namePayment;
    private String description;
    private double feePayment;
    private List<OrderDTO> listOrders;
}
