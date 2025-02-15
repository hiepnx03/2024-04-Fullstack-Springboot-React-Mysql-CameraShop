package com.example.laptop_be.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private long idReview; // Mã đánh giá
    @Column(name = "content")
    private String content; // Nội dung đánh giá
    @Column(name = "rating_point")
    private float ratingPoint; // Điểm xếp hạng
    @Column(name = "timestamp")
    private Timestamp timestamp; // Thời gian mà comment

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_product", nullable = false)
    private Product product; // Đánh giá quyển sách nào

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_user", nullable = false)
    private User user; // Người dùng (ai là người đánh giá)

    @OneToOne( cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_order_detail")
    private OrderDetail orderDetail;
}