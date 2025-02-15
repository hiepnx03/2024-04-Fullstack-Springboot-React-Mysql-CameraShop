package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address extends Base<String> {
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
