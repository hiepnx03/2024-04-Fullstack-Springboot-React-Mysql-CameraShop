package com.example.laptop_be.dao;

import com.example.laptop_be.entity.OrderDetail;
import com.example.laptop_be.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "reviews")
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public Review findReviewByOrderDetail(OrderDetail orderDetail);
    public long countBy();
}
