package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
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
