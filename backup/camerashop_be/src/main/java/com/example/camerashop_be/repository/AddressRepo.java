package com.example.camerashop_be.repository;


import com.example.camerashop_be.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
	Page<Address> findAllByUserIdAndStatus(Long id, Integer status, Pageable pageable);
	List<Address> findAllByIsDefault(Integer isDefault);
}
