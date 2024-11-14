package com.example.laptop_be.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class FeedbacksDTO {
    private int idFeedback;
    private String title;
    private String comment;
    private Date dateCreated;
    private boolean isReaded;
    private UserDTO user;
}