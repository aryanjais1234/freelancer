package com.gateway.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtUtil {

    private final String secretKey = "WnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRf";




    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secretKey.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT Token", e);
        }
    }

    public String extractRole(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    public Long extractUserId(String token) {
        Object userIdObj = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId");

        if (userIdObj instanceof Integer) {
            return ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            return (Long) userIdObj;
        } else if (userIdObj instanceof String) {
            return Long.valueOf((String) userIdObj);
        } else {
            return null;
        }
    }


    public String extractUsername(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // Username is stored as subject
    }


}
