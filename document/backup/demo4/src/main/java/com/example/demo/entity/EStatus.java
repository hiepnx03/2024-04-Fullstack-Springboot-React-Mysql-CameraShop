package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum EStatus {
	INACTIVE(0),
	ACTIVE(1);

	private final Integer name;

	EStatus(Integer name) {
		this.name = name;
	}
}
