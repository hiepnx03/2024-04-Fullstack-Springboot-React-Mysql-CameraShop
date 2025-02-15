package com.example.laptop_be.service;

import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.DeliveryRepository;
import com.example.laptop_be.dto.DeliveryDTO;
import com.example.laptop_be.entity.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private Converter converter;

    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = converter.convertToEntity(deliveryDTO);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return converter.convertToDto(savedDelivery);
    }

    public DeliveryDTO getDeliveryById(int id) {
        Delivery delivery = deliveryRepository.findById(id).orElse(null);
        return converter.convertToDto(delivery);
    }

    public List<DeliveryDTO> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public DeliveryDTO updateDelivery(int id, DeliveryDTO deliveryDTO) {
        Delivery delivery = converter.convertToEntity(deliveryDTO);
        delivery.setIdDelivery(id);
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        return converter.convertToDto(updatedDelivery);
    }

    public void deleteDelivery(int id) {
        deliveryRepository.deleteById(id);
    }
}
