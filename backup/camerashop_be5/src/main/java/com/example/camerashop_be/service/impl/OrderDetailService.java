package com.example.camerashop_be.service.impl;


import com.example.camerashop_be.converter.OrderDetailConverter;
import com.example.camerashop_be.dto.OrderDetailDTO;
import com.example.camerashop_be.entity.OrderDetail;
import com.example.camerashop_be.repository.OrderDetailRepo;
import com.example.camerashop_be.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService implements IOrderDetailService {
	@Autowired
	private OrderDetailRepo orderDetailRepo;

	@Autowired
	private OrderDetailConverter orderDetailConverter;

	@Override
	public List<OrderDetailDTO> getAllByOrderIdAndUserId(Long orderId, Long userId) {
		List<OrderDetail> list = orderDetailRepo.findAllByBillIdAndUserId(orderId,userId);
		return list.stream().map(e -> orderDetailConverter.convertToResponse(e)).collect(Collectors.toList());
	}
	@Override
	public List<OrderDetailDTO> getAllByOrderId(Long orderId) {
		List<OrderDetail> list = orderDetailRepo.findAllByBillId(orderId);
		return list.stream().map(e -> orderDetailConverter.convertToResponse(e)).collect(Collectors.toList());
	}
}
