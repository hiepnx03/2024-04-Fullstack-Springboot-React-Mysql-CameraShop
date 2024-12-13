package com.example.laptop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_delivery")
    private int idDelivery;
    @Column(name = "name_delivery")
    private String nameDelivery;
    @Column(name = "description")
    private String description;
    @Column(name = "fee_delivery")
    private double feeDelivery;
    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listOrders;
}