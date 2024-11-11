package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Order;
import com.example.camerashop_be.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    public List<OrderDetail> findOrderDetailsByOrder(Order order);

}
