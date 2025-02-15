package com.example.camerashop_be.entity;

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
