package com.example.demo.config;

public class EndPoint {
    public static final String font_end_host = "http://localhost:3000";
    // API Authentication
    public static final String AUTH_API = "/api/auth/**";


    public static final String[] PUBLIC_GET = {
            "/admin/products/**",
            "/admin/categories/**"
    };

    public static final String[] PUBLIC_POST = {

    };

    public static final String[] PUBLIC_PUT = {

    };

    public static final String[] PUBLIC_DELETE = {

    };

    public static final String[] ADMIN_ENDPOINT = {
            "/admin/**",
            "/**",
            "/admin/brands/**",
            "/admin/categories/**",
            "/admin/products/**"
    };

    public static final String[] MANAGER_ENDPOINT = {
            "/admin/**",
            "/**",
            "/admin/brands/**",
            "/admin/categories/**",
            "/admin/products/**"
    };


//    public static final String PRODUCT_API_GET = "/admin/products/**";
//    public static final String PRODUCT_API_POST = "/admin/products/**";
//    public static final String PRODUCT_API_PUT = "/admin/products/**";
//    public static final String PRODUCT_API_DELETE = "/admin/products/**";
//
//    // Category Endpoints
//    public static final String CATEGORY_API_GET = "/admin/categories/**";
//    public static final String CATEGORY_API_POST = "/admin/categories/**";
//    public static final String CATEGORY_API_PUT = "/admin/categories/**";
//    public static final String CATEGORY_API_DELETE = "/admin/categories/**";
}
