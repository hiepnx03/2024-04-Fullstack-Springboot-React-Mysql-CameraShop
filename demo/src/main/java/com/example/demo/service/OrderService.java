package com.example.demo.service;


import com.example.demo.dto.ShippingStatusStatistical;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.ShippingStatus;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface OrderService {
	Boolean addOrder(OrderRequest orderRequest);
	Page<OrderResponse> getByUserId(Long userId, Integer page, Integer size);
	OrderResponse getById(Long id);
	OrderResponse updateStatusShipping(Long id,String status);
	Page<OrderResponse> getAll(Integer page, Integer size);
//	Page<OrderResponse> filter(OrderSpecification orderSpecification, int page, int size);
	OrderResponse updateShippingStatus(Long id, ShippingStatus shippingStatus);
	Integer totalOrders();
	Integer totalOrdersInDay(Date date);
	Integer getRevenue();
	Integer getRevenueMonth(Integer i);
	 List<ShippingStatusStatistical> getStatisticalShippingStatus();
}
