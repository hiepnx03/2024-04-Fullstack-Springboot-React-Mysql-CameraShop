package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
	Page<CartItem> findAllByUserId(Long userId, Pageable pageable);
	Optional<CartItem> findByProductIdAndUserId(Long productId, Long userId);
	@Transactional
	void deleteAllByUserId(Long userId);
}
