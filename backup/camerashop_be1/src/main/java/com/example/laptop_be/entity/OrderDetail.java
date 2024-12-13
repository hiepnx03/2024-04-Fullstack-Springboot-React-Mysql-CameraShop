package com.example.laptop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_detail")
    private long idOrderDetail; // Mã chi tiết đơn hàng
    @Column(name = "quantity")
    private int quantity; // Số lượng
    @Column(name = "price")
    private double price; // Giá của 1 don hang
    @Column(name = "is_review")
    private boolean isReview; // đã đánh giá chưa

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "id_product", nullable = false)
    private Product product; // Sách

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_order", nullable = false)
    private Order order; // Đơn hàng
}
