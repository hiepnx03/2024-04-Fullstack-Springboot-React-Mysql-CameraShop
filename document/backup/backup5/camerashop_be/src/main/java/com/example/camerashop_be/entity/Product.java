package com.example.camerashop_be.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "list_price")
    private double listPrice;
    @Column(name = "sell_price")
    private double sellPrice;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "avg_rating")
    private double avgRating;
    @Column(name = "sold_quantity")
    private int soldQuantity;
    @Column(name = "discount_percent")
    private int discountPercent;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "id_product"), inverseJoinColumns = @JoinColumn(name = "id_category"))
    private List<Category> categoryList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> imageList;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviewList;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FavoriteProduct> favoriteProductList;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartItem> listCartItems;
}