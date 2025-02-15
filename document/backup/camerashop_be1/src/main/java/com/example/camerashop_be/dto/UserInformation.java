package com.example.camerashop_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation implements Serializable {
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
}
