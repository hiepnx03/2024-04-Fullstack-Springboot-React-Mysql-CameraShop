package com.example.demo.config;

import com.example.demo.filter.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()  // Cấu hình để vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/cart/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/addresses/**").hasAnyAuthority("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/addresses/**").hasAnyAuthority("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/addresses/**").hasAnyAuthority("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/addresses/**").hasAnyAuthority("ADMIN", "CLIENT")

                        .requestMatchers(HttpMethod.GET, "/admin/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/products/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/products/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/products/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/admin/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/categories/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/admin/user/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/user/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/user/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/user/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/admin/brands/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/brands/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/brands/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/brands/**").hasAuthority("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/admin/vouchers/apply").hasAnyAuthority("ADMIN","CLIENT")
                        .requestMatchers(HttpMethod.GET, "/admin/vouchers/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/vouchers/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/vouchers/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/vouchers/**").hasAuthority("ADMIN")


//                        .requestMatchers(HttpMethod.GET, EndPoint.ADMIN_ENDPOINT).hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.POST, EndPoint.ADMIN_ENDPOINT).hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, EndPoint.ADMIN_ENDPOINT).hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, EndPoint.ADMIN_ENDPOINT).hasAuthority("ADMIN")


                        .requestMatchers(HttpMethod.GET, EndPoint.ADMIN_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.POST, EndPoint.ADMIN_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.PUT, EndPoint.ADMIN_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.DELETE, EndPoint.ADMIN_ENDPOINT).permitAll()


                        .anyRequest().permitAll() // Các yêu cầu khác phải xác thực
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Thêm JWT Filter
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
