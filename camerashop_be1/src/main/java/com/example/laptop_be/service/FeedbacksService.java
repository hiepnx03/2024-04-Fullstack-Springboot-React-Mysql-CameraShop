package com.example.laptop_be.service;
import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.FeedbacksRepository;
import com.example.laptop_be.dto.FeedbacksDTO;
import com.example.laptop_be.entity.Feedbacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class FeedbacksService {
    @Autowired
    private FeedbacksRepository feedbacksRepository;

    @Autowired
    private Converter converter;

    public FeedbacksDTO createFeedbacks(FeedbacksDTO feedbacksDTO) {
        Feedbacks feedbacks = converter.convertToEntity(feedbacksDTO);
        Feedbacks savedFeedbacks = feedbacksRepository.save(feedbacks);
        return converter.convertToDto(savedFeedbacks);
    }

    public FeedbacksDTO getFeedbacksById(int id) {
        Feedbacks feedbacks = feedbacksRepository.findById(id).orElse(null);
        return converter.convertToDto(feedbacks);
    }

    public List<FeedbacksDTO> getAllFeedbacks() {
        List<Feedbacks> feedbacks = feedbacksRepository.findAll();
        return feedbacks.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public FeedbacksDTO updateFeedbacks(int id, FeedbacksDTO feedbacksDTO) {
        Feedbacks feedbacks = converter.convertToEntity(feedbacksDTO);
        feedbacks.setIdFeedback(id);
        Feedbacks updatedFeedbacks = feedbacksRepository.save(feedbacks);
        return converter.convertToDto(updatedFeedbacks);
    }

    public void deleteFeedbacks(int id) {
        feedbacksRepository.deleteById(id);
    }
}