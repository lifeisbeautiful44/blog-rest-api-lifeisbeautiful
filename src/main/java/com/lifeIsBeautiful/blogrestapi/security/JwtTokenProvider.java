package com.lifeIsBeautiful.blogrestapi.security;

import com.lifeIsBeautiful.blogrestapi.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecretKey;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


    //Generate JWT Token
    public String generateToken(Authentication authentication) {
        String usernameOrEmail = authentication.getName();

        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plusMillis(jwtExpirationDate);

        String jwtToken = Jwts.builder()
                .setSubject(usernameOrEmail)
                .setIssuedAt(Date.from(currentDate))
                .setExpiration(Date.from(expirationDate))
                .signWith(key())
                .compact();

        return jwtToken;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecretKey)
        );
    }

    //Get username from JwtToken
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        return username;
    }

    //Validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);

            return true;
        } catch (MalformedJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }

    }

}
