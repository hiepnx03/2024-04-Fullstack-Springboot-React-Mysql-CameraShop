package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tạo token
    public String generateToken(String username, List<String> roles) {
        // Claims có thể để trống hoặc thêm dữ liệu nếu cần
        Map<String, Object> claims = Map.of("roles", roles); // Thêm roles vào claims

        // Tính toán thời gian hết hạn (3 phút sau thời gian hiện tại)
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 3); // 3 phút

        // Xây dựng token
        return Jwts.builder()
                .setClaims(claims)  // Thêm claims vào token (nếu cần)
                .setSubject(username)  // Đặt username làm subject của token
                .setIssuedAt(new Date())  // Đặt thời gian cấp phát token
                .setExpiration(expirationDate)  // Đặt thời gian hết hạn token
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)  // Sử dụng khóa bí mật để ký JWT
                .compact();  // Tạo token cuối cùng
    }


    // Phương thức trích xuất username từ token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Phương thức trích xuất tất cả claims từ token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Phương thức kiểm tra token có hợp lệ hay không
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Phương thức kiểm tra token hết hạn chưa
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Phương thức trích xuất thời gian hết hạn từ token
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Phương thức trích xuất roles từ token
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);

        // Tránh lỗi NullPointerException nếu claim "roles" không tồn tại
        List<String> roles = claims.get("roles", List.class);
        if (roles == null) {
            roles = new ArrayList<>();  // Trả về danh sách rỗng nếu không có roles
        }

        return roles;
    }
}
