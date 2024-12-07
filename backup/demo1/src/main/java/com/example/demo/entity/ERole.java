package com.example.demo.entity;

public enum ERole {
	CLIENT("CLIENT"),
	ADMIN("ADMIN"),
	MANAGER("MANAGER");
	private final String name;

	ERole(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}
}
