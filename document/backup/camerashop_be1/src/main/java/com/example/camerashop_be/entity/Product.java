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
@Table(name = "product")
public class Product extends Base<String> implements Serializable {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
	private String name;
	private Long price;
	@Column(columnDefinition = "TEXT")
	private String description;
	private Integer discount;
	private Integer quantity;
	private Integer status;
	private String slug;
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@OneToMany(mappedBy = "product", targetEntity = Image.class, cascade = CascadeType.ALL)
	private List<Image> images = new ArrayList<>();

}
