package com.example.laptop_be.repository;

import com.example.laptop_be.entity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "feedbacks")
public interface FeedBackRepository extends JpaRepository<Feedbacks, Integer> {
    long countBy();
}
