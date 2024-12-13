package com.example.laptop_be.service;
import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.OrderDetailRepository;
import com.example.laptop_be.dto.OrderDetailDTO;
import com.example.laptop_be.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private Converter converter;

    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = converter.convertToEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return converter.convertToDto(savedOrderDetail);
    }

    public OrderDetailDTO getOrderDetailById(long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        return converter.convertToDto(orderDetail);
    }

    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public OrderDetailDTO updateOrderDetail(long id, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = converter.convertToEntity(orderDetailDTO);
        orderDetail.setIdOrderDetail(id);
        OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
        return converter.convertToDto(updatedOrderDetail);
    }

    public void deleteOrderDetail(long id) {
        orderDetailRepository.deleteById(id);
    }
}