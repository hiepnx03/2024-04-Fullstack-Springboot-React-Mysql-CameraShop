package com.example.camerashop_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private Integer isDefault;
	private String city;
	private String district;
	private String ward;
	private String street;
	private String description;
	private Integer status;
	private Long userId;

}
