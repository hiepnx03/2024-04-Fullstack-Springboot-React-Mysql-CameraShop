package com.example.laptop_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "products")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "list_price")
    private double listPrice; // Giá niêm yết

    @Column(name = "sell_price")
    private double sellPrice; // Giá bán

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity")
    private int quantity; // Số lượng
    @Column(name = "avg_rating")
    private double avgRating; // Trung bình xếp hạng
    @Column(name = "sold_quantity")
    private int soldQuantity; // Đã bán bao nhiêu
    @Column(name = "discount_percent")
    private int discountPercent; // Giảm giá bao nhiêu %


    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private List<Category> categoryList;  // danh sach the loai

    @JsonManagedReference
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Image> imageList;  // danh sach anh

    @JsonManagedReference
    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Review> reviewList; // danh sach danh gia

    @JsonManagedReference
    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH
    })
    private List<OrderDetail> orderDetailList;  // Danh sách chi tiết đơn hàng

    @JsonManagedReference
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FavoriteProduct> favoriteProductList; // Danh sách san pham yêu thích

    @JsonManagedReference
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartItem> listCartItems;

}
