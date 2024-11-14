package com.example.camerashop_be.dto.filter;

import com.example.camerashop_be.entity.Payment;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilter {
	private List<Date> createdDate;
	private Long shippingStatusId;
	private String address;
	private Payment payment;
	private Integer page;
	private Integer size;
}
