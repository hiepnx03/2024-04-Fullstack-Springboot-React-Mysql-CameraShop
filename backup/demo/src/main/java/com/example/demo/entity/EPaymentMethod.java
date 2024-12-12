package com.example.demo.entity;

public enum EPaymentMethod {
    COD("COD"),
    PAYPAL("PAYPAL");
    private final String name;

    EPaymentMethod(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }
}
