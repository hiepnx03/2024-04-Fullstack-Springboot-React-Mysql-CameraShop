package com.example.camerashop_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address extends Base<String> implements Serializable {
	private String firstName;
	private String lastName;
	private String phone;
	private String city;
	private String district;
	private String ward;
	private String street;
	private String description;
	private Integer isDefault;
	private Integer status;
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
}
