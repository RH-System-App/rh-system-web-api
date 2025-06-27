package com.rhsystem.api.rhsystemapi.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class TokenExtractor {

    @Value("${application.jwt.secret}")
    private String secret;

    public String extractUserName(String token) {
        var parser = Jwts.parser().verifyWith(getSignKey()).build();
        var claims = parser.parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
