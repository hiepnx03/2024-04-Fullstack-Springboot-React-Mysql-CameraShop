package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum ERole {
	CLIENT("CLIENT"),
	ADMIN("ADMIN"),
	MANAGER("MANAGER");
	private final String name;


	ERole(String name) {
		this.name = name;
	}
}
