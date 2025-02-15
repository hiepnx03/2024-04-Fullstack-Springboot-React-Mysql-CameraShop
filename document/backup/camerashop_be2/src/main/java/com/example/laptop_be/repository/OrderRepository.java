package com.example.laptop_be.repository;

import com.example.laptop_be.entity.Order;
import com.example.laptop_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "orders")
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Order findFirstByUserOrderByIdOrderDesc(User user);
}
