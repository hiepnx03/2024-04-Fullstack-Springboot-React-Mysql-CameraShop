package com.example.laptop_be.config;

import com.example.laptop_be.security.Endpoints;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Thêm các endpoint PUBLIC_GET
        String[] publicGetEndpoints = Endpoints.PUBLIC_GET;
        for (String endpoint : publicGetEndpoints) {
            registry.addMapping(endpoint)
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET")
                    .allowCredentials(true);
        }

        // Thêm các endpoint PUBLIC_POST
        String[] publicPostEndpoints = Endpoints.PUBLIC_POST;
        for (String endpoint : publicPostEndpoints) {
            registry.addMapping(endpoint)
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("POST")
                    .allowCredentials(true);
        }

        // Thêm các endpoint PUBLIC_PUT
        String[] publicPutEndpoints = Endpoints.PUBLIC_PUT;
        for (String endpoint : publicPutEndpoints) {
            registry.addMapping(endpoint)
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("PUT")
                    .allowCredentials(true);
        }

        // Thêm các endpoint PUBLIC_DELETE
        String[] publicDeleteEndpoints = Endpoints.PUBLIC_DELETE;
        for (String endpoint : publicDeleteEndpoints) {
            registry.addMapping(endpoint)
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("DELETE")
                    .allowCredentials(true);
        }
    }
}
