package com.example.laptop_be.entity;


import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    private int idPayment; // Mã thanh toán
    @Column(name = "name_payment")
    private String namePayment; // Tên thanh toán
    @Column(name = "description")
    private String description; // Mô tả
    @Column(name = "fee_payment")
    private double feePayment; // Chi phí thanh toán

    @OneToMany(mappedBy = "payment",fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Order> listOrders; // Danh sách đơn hàng
}
