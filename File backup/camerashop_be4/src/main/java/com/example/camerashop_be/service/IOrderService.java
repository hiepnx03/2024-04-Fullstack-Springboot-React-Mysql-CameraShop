package com.example.camerashop_be.service;


import com.example.camerashop_be.dto.ShippingStatusStatistical;
import com.example.camerashop_be.dto.request.OrderRequest;
import com.example.camerashop_be.dto.response.OrderResponse;
import com.example.camerashop_be.entity.ShippingStatus;
import com.example.camerashop_be.repository.specs.OrderSpecification;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface IOrderService {
    Boolean addOrder(OrderRequest orderRequest);

    Page<OrderResponse> getByUserId(Long userId, Integer page, Integer size);

    OrderResponse getById(Long id);

    OrderResponse updateStatusShipping(Long id, String status);

    Page<OrderResponse> getAll(Integer page, Integer size);

    Page<OrderResponse> filter(OrderSpecification orderSpecification, int page, int size);

    OrderResponse updateShippingStatus(Long id, ShippingStatus shippingStatus);

    Integer totalOrders();

    Integer totalOrdersInDay(Date date);

    Integer getRevenue();

    Integer getRevenueMonth(Integer i);

    List<ShippingStatusStatistical> getStatisticalShippingStatus();
}
