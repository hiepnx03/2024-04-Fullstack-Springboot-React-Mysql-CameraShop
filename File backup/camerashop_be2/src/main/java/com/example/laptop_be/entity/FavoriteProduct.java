package com.example.laptop_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite_product")
public class FavoriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorite_product")
    private int idFavoriteBook;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
