package com.ktb.community.global.filter;

import com.ktb.community.global.jwt.JwtUtil;
import com.ktb.community.global.validator.RouteValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RouteValidator routeValidator;

    public AuthFilter(final JwtUtil jwtUtil, final RouteValidator routeValidator) {
        this.jwtUtil = jwtUtil;
        this.routeValidator = routeValidator;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return !routeValidator.isSecured.test(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 양식의 토큰입니다.");
        }

        String token = authorization.substring(7);

        try {
            Claims claims = jwtUtil.validateToken(token);
            long id = Long.parseLong(claims.getSubject());

            request.setAttribute("memberId", id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 양식의 토큰입니다.");
        }

        filterChain.doFilter(request, response);
    }

}
