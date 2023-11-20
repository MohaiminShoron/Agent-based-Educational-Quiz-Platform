package com.seng696.Seng696.security;

import com.seng696.Seng696.config.ApplicationProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    ApplicationProperties applicationProperties;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(applicationProperties.getJwtTokenSecret()).parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + applicationProperties.getJwtExpireTime()))
                .signWith(SignatureAlgorithm.HS256, applicationProperties.getJwtTokenSecret()).compact();
    }

    public String generateToken(String msisdn) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, msisdn);
    }

    public boolean validateToken(String token, String msisdn) {
        try {
            final String userName = extractUserName(token);
            return (userName.equals(msisdn) && !isTokenExpired(token));

        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}