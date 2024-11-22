package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Column(name = "[image_order]")
    private Long imageOrder; // Order of the image

    @ManyToOne(fetch = FetchType.LAZY) // Mặc định là LAZY, có thể chuyển thành EAGER nếu cần
    @JoinColumn(name = "product_id", nullable = false) // Cột `product_id` trong bảng `images`
    private Product product;


    // Override hashCode and equals using only `id`
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return id != null && id.equals(image.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // Getters and setters
}
