package com.example.camerashop_be.dto.filter;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterUser {
	private Integer status;
	private String role;
	private List<Date> createdAt;
	private String email;
	private Integer page;
	private Integer size;

}
