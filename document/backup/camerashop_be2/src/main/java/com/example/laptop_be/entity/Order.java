package com.example.laptop_be.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private int idOrder;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "delivery_address")
    private String deliveryAddress;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "total_price_product")
    private double totalPriceProduct;
    @Column(name = "fee_delivery")
    private double feeDelivery;
    @Column(name = "fee_payment")
    private double feePayment;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "status")
    private String status;
    @Column(name = "note")
    private String note;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> listOrderDetails;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_payment")
    private Payment payment;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_delivery")
    private Delivery delivery;
}