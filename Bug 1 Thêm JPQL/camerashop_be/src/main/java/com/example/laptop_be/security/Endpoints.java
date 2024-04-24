package com.example.laptop_be.security;

public class Endpoints {

    public static final String font_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET = {
           "/products",
            "/**",
            "/api/products",
            "/category",
    };

    public static final String[] PUBLIC_POST = {
            "/products",
            "/api/category",
            "/category",
            "/**",
    };

    public static final String[] PUBLIC_PUT = {
            "/products",
            "/api/category",
            "/category",
            "/**",
    };

    public static final String[] PUBLIC_DELETE = {
            "/products",
            "/api/category",
            "/category",
            "/**",
    };

    public static final String[] ADMIN_ENDPOINT = {
            "/**",
            "/products",
            "/api/category",
            "/category",
    };
}