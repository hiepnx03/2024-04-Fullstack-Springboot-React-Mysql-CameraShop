package com.example.demo.service;

import com.example.demo.entity.ShippingStatus;

import java.util.List;

public interface ShippingStatusService {
    public ShippingStatus getByName(String name);
    public List<ShippingStatus> getAll();
}
