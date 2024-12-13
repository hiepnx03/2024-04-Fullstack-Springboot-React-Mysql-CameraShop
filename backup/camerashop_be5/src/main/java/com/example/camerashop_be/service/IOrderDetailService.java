package com.example.camerashop_be.service;


import com.example.camerashop_be.dto.OrderDetailDTO;

import java.util.List;

public interface IOrderDetailService {
	List<OrderDetailDTO> getAllByOrderIdAndUserId(Long orderId, Long userId);
	List<OrderDetailDTO> getAllByOrderId(Long orderId);
}
