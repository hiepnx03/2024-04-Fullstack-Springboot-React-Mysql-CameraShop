package com.example.camerashop_be.service;

import com.example.camerashop_be.converter.Converter;
import com.example.camerashop_be.dto.OrderDTO;
import com.example.camerashop_be.entity.Order;
import com.example.camerashop_be.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Converter converter;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = converter.convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return converter.convertToDto(savedOrder);
    }

    public OrderDTO getOrderById(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        return converter.convertToDto(order);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        Order order = converter.convertToEntity(orderDTO);
        order.setIdOrder(id);
        Order updatedOrder = orderRepository.save(order);
        return converter.convertToDto(updatedOrder);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}