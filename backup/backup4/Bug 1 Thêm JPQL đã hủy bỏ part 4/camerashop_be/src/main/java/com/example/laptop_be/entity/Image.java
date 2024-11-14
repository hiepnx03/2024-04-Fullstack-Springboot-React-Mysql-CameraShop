package com.example.laptop_be.entity;


import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private int idImage; // Mã ảnh
    @Column(name = "name_image")
    private String nameImage; // Tên ảnh
    @Column(name = "is_thumbnail")
    private boolean isThumbnail; // Có phải là thumbnail không
    @Column(name = "url_image")
    private String urlImage; // Link hình ảnh
    @Column(name = "data_image", columnDefinition = "LONGTEXT")
    @Lob
    private String dataImage; // Dữ liệu ảnh

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_product", nullable = false)
    private Product product; // Thuộc quyển sách nào

}
