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
    private int idDelivery; // Mã giao hàng
    @Column(name = "name_delivery")
    private String nameDelivery; // Tên giao hàng
    @Column(name = "description")
    private String description; // Mô tả
    @Column(name = "fee_delivery")
    private double feeDelivery; // Chi phí giao hàng

    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Order> listOrders; // Danh sách đơn hàng
}
