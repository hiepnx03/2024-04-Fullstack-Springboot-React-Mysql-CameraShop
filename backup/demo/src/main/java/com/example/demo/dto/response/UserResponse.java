package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
	private Long id;
	private String email;
	private String userName;
	private String firstName;
	private String lastName;
	private Integer status;
	private List<String> roles;
}
