package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum EStatusPayment {
    PAID("PAID"),
    UNPAID("UNPAID");

    private final String name;

    EStatusPayment(String name) {
        this.name = name;
    }


}
