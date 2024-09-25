package com.example.camerashop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private long idReview;
    @Column(name = "content")
    private String content;
    @Column(name = "rating_point")
    private float ratingPoint;
    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order_detail")
    private OrderDetail orderDetail;
}