package com.example.demo.dto;


import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDTO<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;

    public PageDTO(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

    // Getters and Setters
}