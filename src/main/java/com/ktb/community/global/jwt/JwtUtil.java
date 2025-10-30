package com.ktb.community.global.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.community.dto.auth.response.LoginResponse;
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

    @Value("${spring.jwt.access_exp_time}")
    private long accessExpTime;

    @Value("${spring.jwt.refresh_exp_time}")
    private long refreshExpTime;

    @Value("${spring.jwt.key}")
    private String jwtKey;

    private final ObjectMapper objectMapper;

    public JwtUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Claims validateToken(String token) throws JwtException {
        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKey));
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(tokenKey).build().parseClaimsJws(token);
        return claims.getBody();
    }


    public LoginResponse createTokens(final long memberId){
        String accessToken = createToken(memberId, accessExpTime);
        String refreshToken = createToken(memberId, refreshExpTime);

        return new LoginResponse(memberId, accessToken, refreshToken);
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
