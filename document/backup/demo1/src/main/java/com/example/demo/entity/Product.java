package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
public class Product extends Base<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_category", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "product_id"), // Khóa ngoại cho bảng sản phẩm
            inverseJoinColumns = @JoinColumn(name = "category_id") // Khóa ngoại cho bảng danh mục
    )
    private Set<Category> categories = new HashSet<>(); // Khởi tạo để tránh NullPointerException

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();


    // Override hashCode and equals using only `id`
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

