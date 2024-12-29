package com.example.demo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKeyString; // Khóa bí mật từ file cấu hình

    private SecretKey getSecretKey() {
        // Chuyển chuỗi bí mật thành SecretKey
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())  // Sử dụng SecretKey từ getSecretKey()
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has expired at " + e.getClaims().getExpiration(), e);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token: " + token, e);
        } catch (MalformedJwtException e) {
            throw new JwtException("Malformed JWT token, please check the token structure", e);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature, signature mismatch", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Token is empty or null", e);
        }
    }



    // Tạo token
    public String generateToken(String username, List<String> roles) {
        Map<String, Object> claims = Map.of("roles", roles);

        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 3);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // Sử dụng SecretKey
                .compact();
    }


    // Trích xuất username từ token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Trích xuất tất cả claims từ token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey()) // Sử dụng SecretKey
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Kiểm tra token có hợp lệ hay không
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Kiểm tra token hết hạn chưa
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Trích xuất thời gian hết hạn từ token
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Trích xuất roles từ token
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles != null ? roles : new ArrayList<>();
    }
}
