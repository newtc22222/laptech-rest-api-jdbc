package com.laptech.restapi.jwt.util;

import com.laptech.restapi.common.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * <a href="https://www.youtube.com/watch?v=rBNOc4ymd1E">JWT</a>
 *
 * @since 2023-01-04
 */
@Component
@PropertySource("classpath:token.properties")
public class JwtUtil {
    @Value("${secret_key}")
    private String SECRET_KEY;
    @Value("${access_token_time}")
    private Long access_token_time;

    public String getUserPhoneFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String phone = getUserPhoneFromToken(token);
        return phone.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateJwtAccessToken(UserDetails userDetails) {
        long timestamp = System.currentTimeMillis();

        return Jwts.builder()
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + access_token_time)) // 1 hour
                .setSubject(userDetails.getUsername()) // Actually, it's a phone
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

