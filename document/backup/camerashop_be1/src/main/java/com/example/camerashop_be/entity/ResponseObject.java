package com.example.camerashop_be.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject implements Serializable {
	private String status;
	private String message;
	private Object data;
}
