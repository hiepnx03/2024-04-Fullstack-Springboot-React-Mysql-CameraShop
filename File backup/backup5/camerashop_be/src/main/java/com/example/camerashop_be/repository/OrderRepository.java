package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Order;
import com.example.camerashop_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Order findFirstByUserOrderByIdOrderDesc(User user);
}
