package com.example.demo.dto.response;

import com.example.demo.entity.Payment;
import com.example.demo.entity.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long total;
    private Date createdDate;
    private ShippingStatus shippingStatus;
    private String address;
    private String fullName;
    private String phone;
    private Payment payment;
    private String createdBy;
}
