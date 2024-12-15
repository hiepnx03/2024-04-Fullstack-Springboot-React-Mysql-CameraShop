package com.example.demo.repository;


import com.example.demo.entity.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingStatusRepository extends JpaRepository<ShippingStatus, Long> {
    ShippingStatus findByName(String name);


}
