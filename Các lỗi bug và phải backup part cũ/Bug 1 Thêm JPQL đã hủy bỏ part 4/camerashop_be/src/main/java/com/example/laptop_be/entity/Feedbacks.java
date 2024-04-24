package com.example.laptop_be.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import jakarta.persistence.*;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "feedback")
public class Feedbacks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback")
    private int idFeedback;
    @Column(name = "title")
    private String title;
    @Column(name = "comment")
    private String comment;
    @Column(name = "dateCreated")
    private Date dateCreated;
    @Column(name = "isReaded")
    private boolean isReaded;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "Feedbacks{" +
                "idFeedback=" + idFeedback +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", dateCreated=" + dateCreated +
                ", isReaded=" + isReaded +
                '}';
    }
}
