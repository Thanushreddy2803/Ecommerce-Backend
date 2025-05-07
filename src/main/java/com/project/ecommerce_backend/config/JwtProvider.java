package com.project.ecommerce_backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtProvider {

    // Secret key for signing JWT, should be stored securely in a production environment
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Method to generate a JWT token based on the provided authentication
    public String generateToken(Authentication auth) {
        long expirationTime = 1000 * 60 * 60 * 24;  // 1 day expiration in milliseconds
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("email", auth.getName()) // You can add more claims here like roles, permissions, etc.
                .signWith(key)
                .compact();
    }

    // Method to extract email from the JWT token
    public String getEmailFromToken(String jwt) {
        try {
            // Remove "Bearer " prefix if it exists
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return String.valueOf(claims.get("email"));
        } catch (Exception e) {
            // Handle invalid or expired token scenario
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }

    // Method to check if the token has expired
    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }
}
