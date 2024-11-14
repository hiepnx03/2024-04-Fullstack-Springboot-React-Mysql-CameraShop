package com.example.camerashop_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
	private Long id;
	private String email;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Integer status;
	private String[] roles;
}
