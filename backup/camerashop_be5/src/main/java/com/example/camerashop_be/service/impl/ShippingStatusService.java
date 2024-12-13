package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.entity.ShippingStatus;
import com.example.camerashop_be.repository.ShippingStatusRepo;
import com.example.camerashop_be.service.IShippingStatusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ShippingStatusService implements IShippingStatusService {
    private final ShippingStatusRepo shippingStatusRepo;

    public final ShippingStatus getByName(String name) {
        return shippingStatusRepo.findByName(name);
    }

    @Override
    public List<ShippingStatus> getAll() {
        return shippingStatusRepo.findAll();
    }
}
