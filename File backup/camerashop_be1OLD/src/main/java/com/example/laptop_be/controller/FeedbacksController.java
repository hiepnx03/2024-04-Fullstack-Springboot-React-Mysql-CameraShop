package com.example.laptop_be.controller;

import com.example.laptop_be.dto.FeedbacksDTO;
import com.example.laptop_be.service.FeedbacksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbacksController {

    @Autowired
    private FeedbacksService feedbacksService;

    @PostMapping
    public ResponseEntity<FeedbacksDTO> createFeedback(@RequestBody FeedbacksDTO feedbacksDTO) {
        return ResponseEntity.ok(feedbacksService.createFeedbacks(feedbacksDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbacksDTO> getFeedbackById(@PathVariable int id) {
        return ResponseEntity.ok(feedbacksService.getFeedbacksById(id));
    }

    @GetMapping
    public ResponseEntity<List<FeedbacksDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbacksService.getAllFeedbacks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbacksDTO> updateFeedback(@PathVariable int id, @RequestBody FeedbacksDTO feedbacksDTO) {
        return ResponseEntity.ok(feedbacksService.updateFeedbacks(id, feedbacksDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable int id) {
        feedbacksService.deleteFeedbacks(id);
        return ResponseEntity.noContent().build();
    }
}
