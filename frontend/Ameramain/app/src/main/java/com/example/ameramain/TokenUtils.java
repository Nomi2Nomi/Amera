package com.example.ameramain;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class TokenUtils {

    private static final String SECRET_KEY = "nominominominominominominominominominominominominominominominominominomi"; // должен быть ≥ 64 байта
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // Извлечение userId из JWT токена
    public static Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userId", Long.class);  // Извлекаем userId
        } catch (JwtException e) {
            System.err.println("Error extracting userId from token: " + e.getMessage());
            return null;
        }
    }

}
