package com.example.camerashop_be.dto.response;


import com.example.camerashop_be.entity.Payment;
import com.example.camerashop_be.entity.ShippingStatus;
import lombok.*;

import java.util.Date;

@Data
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
