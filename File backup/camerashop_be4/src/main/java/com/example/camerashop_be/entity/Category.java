package com.example.camerashop_be.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer status;
	private String slug;
	@OneToMany(mappedBy = "category", targetEntity = Product.class, cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();
}
