package com.example.demo.entity;

public enum EShippingStatus {
	VERIFIED("VERIFIED"),
	UNVERIFIED("UNVERIFIED"),
	DELIVERING("DELIVERING"),
	DELIVERED("DELIVERED"),
	CANCELED("CANCELED"),
	CANCELING("CANCELING");

	private final String name;

	EShippingStatus(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}
}
