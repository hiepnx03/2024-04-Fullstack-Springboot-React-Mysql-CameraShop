package com.example.demo.repository;


import com.example.demo.entity.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingStatusRepository extends JpaRepository<ShippingStatus, Integer> {
    Optional<ShippingStatus> findByName(String name);  // Tìm trạng thái giao hàng theo tên

}
