package com.rhsystem.api.rhsystemapi.infrastructure.security;

import com.rhsystem.api.rhsystemapi.core.security.TokenGenerator;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenGenerator implements TokenGenerator<String, User> {

    private final Long expirationTime = Duration.ofMinutes(15).toMillis();
    @Value("${application.jwt.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {
        final SecureDigestAlgorithm<SecretKey, SecretKey> alg = Jwts.SIG.HS512;
        return Jwts.builder()
                   .subject(user.getKey().getValue().toString())
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(getSignKey(), alg)
                   .compact();
    }


    private SecretKey getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
