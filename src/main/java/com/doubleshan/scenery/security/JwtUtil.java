package com.doubleshan.scenery.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXP_MS = 3600_000; // 1h

    public static String generateToken(String userId, String role) {
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(Map.of("role", role))
                .setExpiration(new Date(System.currentTimeMillis() + EXP_MS))
                .signWith(KEY)
                .compact();
    }

    public static JwtUser parse(String token) {
        var parser = Jwts.parserBuilder().setSigningKey(KEY).build();
        var claims = parser.parseClaimsJws(token).getBody();
        return new JwtUser(claims.getSubject(), (String) claims.get("role"));
    }

    public static class JwtUser {
        public final String userId;
        public final String role;

        public JwtUser(String u, String r) {
            this.userId = u;
            this.role = r;
        }
    }
}
