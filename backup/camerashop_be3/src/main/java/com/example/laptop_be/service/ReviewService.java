package com.example.laptop_be.service;

import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.ReviewRepository;
import com.example.laptop_be.dto.ReviewDTO;
import com.example.laptop_be.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// ReviewService
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private Converter converter;

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = converter.convertToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return converter.convertToDto(savedReview);
    }

    public ReviewDTO getReviewById(long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        return converter.convertToDto(review);
    }

    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public ReviewDTO updateReview(long id, ReviewDTO reviewDTO) {
        Review review = converter.convertToEntity(reviewDTO);
        review.setIdReview(id);
        Review updatedReview = reviewRepository.save(review);
        return converter.convertToDto(updatedReview);
    }

    public void deleteReview(long id) {
        reviewRepository.deleteById(id);
    }
}
