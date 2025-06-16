package com.ebusiness.ebusiness.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class TokenGenerator {

    private static final Key KEY = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication auth) {
        String name = auth.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(name)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(KEY, SignatureAlgorithm.HS512)
                .compact();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }

}
