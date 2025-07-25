package com.soulstock.backend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateJwtToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public JwtTokenStatus validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return JwtTokenStatus.VALID;
        } catch (ExpiredJwtException e) {
            return JwtTokenStatus.EXPIRED;
        } catch (SignatureException e) {
            return JwtTokenStatus.INVALID_SIGNATURE;
        } catch (MalformedJwtException e) {
            return JwtTokenStatus.MALFORMED;
        } catch (UnsupportedJwtException e) {
            return JwtTokenStatus.UNSUPPORTED;
        } catch (IllegalArgumentException e) {
            return JwtTokenStatus.EMPTY;
        } catch (JwtException e) {
            return JwtTokenStatus.UNKNOWN_ERROR;
        }
    }
}