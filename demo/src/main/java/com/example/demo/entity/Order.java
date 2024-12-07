package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill")
public class Order extends Base<String> {

    private Long shippingCost;
    private Long total;
    private String description;
    private String address;
    private String phone;
    private String fullName;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdDate;
    @Column(name = "status")
    private Integer status;


    @ManyToOne(targetEntity = ShippingStatus.class)
    @JoinColumn(name = "shipping_status_id", nullable = false)
    private ShippingStatus shippingStatus;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bill",
            cascade = CascadeType.ALL,
            targetEntity = OrderDetail.class
    )
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @ManyToOne(targetEntity = Voucher.class)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    @PrePersist
    private void onCreate() {
        createdDate = new Date();
    }

//
//    @ManyToOne(targetEntity = ShippingStatus.class)
//    @JoinColumn(name = "shipping_status_id", nullable = false)
//    private ShippingStatus shippingStatus;


}
