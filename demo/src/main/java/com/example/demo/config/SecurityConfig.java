package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()  // Cấu hình để vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/admin/products").permitAll()
                        .requestMatchers("/admin/products/**").permitAll()


//                        .requestMatchers(HttpMethod.GET, "/products/**").hasAnyAuthority("USER", "ADMIN")  // Cho phép quyền USER và ADMIN xem sản phẩm
//                        .requestMatchers(HttpMethod.POST, "/products/**").hasAuthority("ADMIN")  // Chỉ ADMIN có thể tạo sản phẩm
//                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority("ADMIN")  // Chỉ ADMIN có thể sửa sản phẩm
//                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("ADMIN")  // Chỉ ADMIN có thể xóa sản phẩm

                        .anyRequest().permitAll() // Các yêu cầu khác phải xác thực
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // Sử dụng session stateless (JWT)
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
