package com.ktb.community.global.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final String jwtKey;
    private final ObjectMapper objectMapper;

    public JwtUtil(@Value("${spring.jwt.key}") String jwtKey, ObjectMapper objectMapper) {
        this.jwtKey = jwtKey;
        this.objectMapper = objectMapper;
    }

    public Claims validateToken(String token) throws JwtException {
        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKey));
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(tokenKey).build().parseClaimsJws(token);

        return claims.getBody();
    }

    public String createToken(final long memberId, final long expired) {

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expired);

        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKey));

        try {
            String loginMemberJson = objectMapper.writeValueAsString(memberId);

            return Jwts.builder()
                    .setId(UUID.randomUUID().toString())
                    .setIssuer("community.com")
                    .setSubject(loginMemberJson)
                    .setIssuedAt(now)
                    .setExpiration(expiredDate)
                    .signWith(tokenKey, SignatureAlgorithm.HS256)
                    .compact();

        }catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "JSON PARSE EXCEPTION");
        }
    }
}
