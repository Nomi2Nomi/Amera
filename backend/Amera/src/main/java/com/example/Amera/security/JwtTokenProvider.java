package com.example.Amera.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final long EXPIRATION_TIME = 86400000;
    private final String SECRET_KEY = "nominominominominominominominominominominominominominominominominominomi"; //512 bits or 64 bytes
    private final Key key = generateKeyFromSecret(SECRET_KEY);

    // Генерация ключа из строки с учетом длины
    private Key generateKeyFromSecret(String secret) {
        byte[] keyBytes = secret.getBytes();
        
        // Если длина ключа меньше 512 бит, добавляем дополнительные байты
        if (keyBytes.length < 64) {
            byte[] extendedKey = new byte[64];
            System.arraycopy(keyBytes, 0, extendedKey, 0, keyBytes.length);
            return Keys.hmacShaKeyFor(extendedKey);  // H512
        }
        
        // Если ключ уже достаточной длины, используем его
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String email, Long userId) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

            // Создаем токен с userId в качестве claim
            return Jwts.builder()
                    .setSubject(email)
                    .claim("userId", userId)  // Добавляем userId как claim
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

        } catch (Exception e) {
            System.err.println("Error generating token: " + e.getMessage());
            return null;
        }
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Date expiration = claimsJws.getBody().getExpiration();
            if (expiration.before(new Date())) {
                System.err.println("Token has expired");
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("JWT Token is invalid: " + e.getMessage());
            return false;
        }
    }

    // Метод для извлечения userId из токена
    public Long extractUserId(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return claimsJws.getBody().get("userId", Long.class);  // Извлекаем userId из claim
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("JWT Token is invalid: " + e.getMessage());
            return null;
        }
    }
    
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
