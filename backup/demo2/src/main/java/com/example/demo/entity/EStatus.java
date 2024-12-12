package com.example.demo.entity;

public enum EStatus {
	INACTIVE(0),
	ACTIVE(1);

	private final Integer name;

	EStatus(Integer s) {
		name = s;
	}

	public Integer getName() {
		return name;
	}
}
