package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum EPaymentMethod {
    COD("COD"),
    PAYPAL("PAYPAL"),
    VNPAY("VNPAY"),
    ZALOPAY("ZALOPAY"),
    MOMO("MOMO");

    private final String name;

    EPaymentMethod(String name) {
        this.name = name;
    }
}
