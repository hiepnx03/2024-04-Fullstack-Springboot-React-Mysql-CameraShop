package com.example.camerashop_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import jakarta.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer quantity;
	private Integer discount;
	private Long price;
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "product_id", nullable = false)
	@JsonIgnore
	private Product product;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(targetEntity = Order.class)
	@JoinColumn(name = "bill_id", nullable = false)
	private Order bill;
}
