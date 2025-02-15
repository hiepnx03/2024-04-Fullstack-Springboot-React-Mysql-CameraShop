package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum EShippingStatus {
	VERIFIED("VERIFIED"),
	UNVERIFIED("UNVERIFIED"),
	DELIVERING("DELIVERING"),
	DELIVERED("DELIVERED"),
	CANCELED("CANCELED"),
	CANCELING("CANCELING");

	private final String name;

	EShippingStatus(String name) {
		this.name = name;
	}
}
