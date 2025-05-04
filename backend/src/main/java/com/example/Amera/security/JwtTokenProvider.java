package com.example.Amera.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final long EXPIRATION_TIME = 86400000; // 24 часа
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Надежный ключ

    public String createToken(String email) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

            String token = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            System.out.println("Generated token: " + token);
            return token;
        } catch (Exception e) {
            System.out.println("Error generating token: " + e.getMessage());
            return null;
        }
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            // Проверка истечения токена
            Date expiration = claimsJws.getBody().getExpiration();
            if (expiration.before(new Date())) {
                System.err.println("Token has expired");
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Логирование ошибки
            System.err.println("JWT Token is invalid: " + e.getMessage());
            return false;
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
