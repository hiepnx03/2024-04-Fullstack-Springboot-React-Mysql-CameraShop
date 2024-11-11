package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.OrderDetail;
import com.example.camerashop_be.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public Review findReviewByOrderDetail(OrderDetail orderDetail);
    public long countBy();
}
