package com.example.demo.converter;

import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.request.OrderDetailRequest;
import com.example.demo.entity.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderDetailConverter {
    private final ModelMapper modelMapper;

    public OrderDetailConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDetail convertToEntity(OrderDetailRequest orderDetailDTO) {

        return modelMapper.map(orderDetailDTO, OrderDetail.class);
    }

    public OrderDetailDTO convertToResponse(OrderDetail orderDetail) {
        ImageDTO imageDTO = modelMapper.map(orderDetail.getProduct().getImages().getClass(), ImageDTO.class);
        OrderDetailDTO result = modelMapper.map(orderDetail, OrderDetailDTO.class);
        result.getProduct().setImage(imageDTO);
        return result;
    }
}
