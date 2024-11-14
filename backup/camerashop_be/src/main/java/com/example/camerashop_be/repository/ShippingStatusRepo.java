package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingStatusRepo extends JpaRepository<ShippingStatus, Long> {
	ShippingStatus findByName(String name);
}
