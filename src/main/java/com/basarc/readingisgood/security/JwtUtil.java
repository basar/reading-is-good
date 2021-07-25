package com.basarc.readingisgood.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.expiration-in-millis}")
    private Long tokenExpirationInMillis;

    @NonNull
    public String generateJwtToken(@NonNull final String username) {

        if(!StringUtils.hasLength(username)){
            throw new IllegalArgumentException("Username must not be null or empty string");
        }

        log.debug("Token will be generated for {}", username);
        final long currentTimeMillis = System.currentTimeMillis();
        final SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + tokenExpirationInMillis))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    @Nullable
    private Claims getAllClaimsFromToken(@NonNull final String token) {

        Claims claims;
        final SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
        try {
            claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token).getBody();
        } catch (JwtException ex) {
            log.debug("An exception occurred while parsing the token", ex);
            claims = null;
        }
        return claims;
    }


    @Nullable
    public String getUsernameFromToken(final String token){
        Claims claims = getAllClaimsFromToken(token);
        return claims !=null ? claims.getSubject() : null;
    }

    public boolean isTokenExpired(final String token){
        Claims claims = getAllClaimsFromToken(token);
        return claims != null && claims.getExpiration().before(new Date());
    }

}
