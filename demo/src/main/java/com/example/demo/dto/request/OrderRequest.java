package com.example.demo.dto.request;

import com.example.demo.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    private Long id;
    private Long shippingCost;
    private String description;
    private String address;
    private Long userId;
    private String phone;
    private String fullName;
    private List<OrderDetailRequest> orderDetails = new ArrayList<>();
    private Payment payment;
}
