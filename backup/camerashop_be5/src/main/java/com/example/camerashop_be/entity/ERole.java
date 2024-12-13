package com.example.camerashop_be.entity;

public enum ERole {
	CLIENT("client"),
	ADMIN("admin"),
	MANAGER("manager");
	private final String name;

	ERole(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}
}
