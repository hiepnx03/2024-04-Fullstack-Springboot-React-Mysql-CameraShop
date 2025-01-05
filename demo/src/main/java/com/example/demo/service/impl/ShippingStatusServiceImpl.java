package com.example.demo.service.impl;

import com.example.demo.entity.ShippingStatus;
import com.example.demo.repository.ShippingStatusRepository;
import com.example.demo.service.ShippingStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShippingStatusServiceImpl implements ShippingStatusService {
    private final ShippingStatusRepository shippingStatusRepository;

    @Override
    public ShippingStatus getByName(String name) {
        return shippingStatusRepository.findByName(name);
    }

    @Override
    public List<ShippingStatus> getAll() {
        return shippingStatusRepository.findAll();
    }
}
