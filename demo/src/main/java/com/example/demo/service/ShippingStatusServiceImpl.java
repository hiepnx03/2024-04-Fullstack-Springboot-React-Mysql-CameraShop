package com.example.demo.service;

import com.example.demo.entity.ShippingStatus;
import com.example.demo.repository.ShippingStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
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
