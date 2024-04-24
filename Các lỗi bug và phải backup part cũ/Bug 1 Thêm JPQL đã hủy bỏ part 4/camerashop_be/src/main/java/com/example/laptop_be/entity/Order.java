package com.example.laptop_be.entity;


import lombok.Data;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private int idOrder; // Mã đơn hàng
    @Column(name = "date_created")
    private Date dateCreated; // Ngày tạo giỏ hàng
    @Column(name = "delivery_address")
    private String deliveryAddress; // Địa chỉ giao hàng
    @Column(name = "phone_number")
    private String phoneNumber; // Số điện thoại (vì có thể tuỳ chỉnh)
    @Column(name = "full_name")
    private String fullName; // Họ và tên của khách hàng (tuỳ chỉnh)
    @Column(name = "total_price_product")
    private double totalPriceProduct; // Tổng tiền sản phẩm
    @Column(name = "fee_delivery")
    private double feeDelivery; // Chi phí giao hàng
    @Column(name = "fee_payment")
    private double feePayment; // Chi phí thanh toán
    @Column(name = "total_price")
    private double totalPrice; // Tổng tiền
    @Column(name = "status")
    private String status; // Trạng thái của đơn hàng
    @Column(name = "note")
    private String note; // Ghi chú

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> listOrderDetails; // Danh sách chi tiết đơn hàng

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_user", nullable = false)
    private User user; // Người dùng

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_payment")
    private Payment payment; // Hình thức thanh toán

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_delivery")
    private Delivery delivery; // Hình thức giao hàng
}
