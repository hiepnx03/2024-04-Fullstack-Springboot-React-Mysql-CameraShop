package com.example.demo.filter;

import com.example.demo.util.JwtUtils;
import com.example.demo.util.TokenBlacklist;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklist tokenBlacklist;

    public JwtFilter(JwtUtils jwtUtil, UserDetailsService userDetailsService, TokenBlacklist tokenBlacklist) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        // Kiểm tra nếu header Authorization không có hoặc không bắt đầu bằng "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  // Tiếp tục lọc yêu cầu mà không xác thực token
            return;
        }

        String token = authorizationHeader.substring(7);

        // Kiểm tra nếu token bị blacklist
        if (tokenBlacklist.isTokenBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has been revoked");
            return;
        }

        String username = jwtUtil.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                var userDetails = userDetailsService.loadUserByUsername(username);

                // Kiểm tra tính hợp lệ của token và username
                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    // Lấy roles từ token và đảm bảo không null
                    List<String> roles = jwtUtil.extractRoles(token);
                    if (roles == null || roles.isEmpty()) {
                        roles = List.of();  // Tránh NullPointerException, trả về danh sách rỗng nếu không có roles
                    }

                    // Chuyển roles sang SimpleGrantedAuthority
                    var authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    // Đặt Authentication vào SecurityContext
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Xử lý lỗi khi có ngoại lệ trong quá trình xác thực token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT token: " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response); // Tiếp tục xử lý yêu cầu
    }

}
