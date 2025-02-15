package com.example.camerashop_be.entity;

public enum EStatusPayment {
	PAID("PAID"),
	UNPAID("UNPAID");

	private final String name;

	EStatusPayment(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}
}
