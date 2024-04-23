package com.example.laptop_be.security;

public class Endpoints {

    public static final String font_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET = {
           "/products",
            "/**",
            "/api/products",
    };

    public static final String[] PUBLIC_POST = {
            "/products"
    };

    public static final String[] PUBLIC_PUT = {

    };

    public static final String[] PUBLIC_DELETE = {

    };

    public static final String[] ADMIN_ENDPOINT = {
            "/**",
    };
}