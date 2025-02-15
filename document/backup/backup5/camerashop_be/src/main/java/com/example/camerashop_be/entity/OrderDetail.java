package com.example.camerashop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_detail")
    private long idOrderDetail;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;
    @Column(name = "is_review")
    private boolean isReview;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;
}