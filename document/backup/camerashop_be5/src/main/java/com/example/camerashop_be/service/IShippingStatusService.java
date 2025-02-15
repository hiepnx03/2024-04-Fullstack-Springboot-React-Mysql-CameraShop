package com.example.camerashop_be.service;


import com.example.camerashop_be.entity.ShippingStatus;

import java.util.List;

public interface IShippingStatusService {
    ShippingStatus getByName(String name);

    List<ShippingStatus> getAll();
}
