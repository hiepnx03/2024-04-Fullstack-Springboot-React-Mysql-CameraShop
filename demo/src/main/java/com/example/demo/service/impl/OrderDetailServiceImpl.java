package com.example.demo.service.impl;


import com.example.demo.converter.OrderDetailConverter;
import com.example.demo.repository.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl {
	private final OrderDetailRepository orderDetailRepository;
	private final OrderDetailConverter orderDetailConverter;

//	@Override
//	public List<OrderDetailDTO> getAllByOrderIdAndUserId(Long orderId, Long userId) {
//		List<OrderDetail> list = orderDetailRepository.findAllByBillIdAndUserId(orderId,userId);
//		return list.stream().map(e -> orderDetailConverter.convertToResponse(e)).collect(Collectors.toList());
//	}
//	@Override
//	public List<OrderDetailDTO> getAllByOrderId(Long orderId) {
//		List<OrderDetail> list = orderDetailRepository.findAllByBillId(orderId);
//		return list.stream().map(e -> orderDetailConverter.convertToResponse(e)).collect(Collectors.toList());
//	}
}
