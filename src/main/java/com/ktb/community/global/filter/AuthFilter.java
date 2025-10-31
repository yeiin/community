package com.ktb.community.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.community.dto.Response;
import com.ktb.community.global.constant.StatusCode;
import com.ktb.community.global.exception.CustomUnauthorizedException;
import com.ktb.community.global.provider.JwtProvider;
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

import static com.ktb.community.global.constant.ExceptionConstant.INVALID_TOKEN;

@Slf4j
@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RouteValidator routeValidator;
    private final ObjectMapper objectMapper;


    public AuthFilter(final JwtProvider jwtProvider, final RouteValidator routeValidator, final ObjectMapper objectMapper) {
        this.jwtProvider = jwtProvider;
        this.routeValidator = routeValidator;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return !routeValidator.isSecured.test(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            handleException(response,INVALID_TOKEN.message());
            return;
        }

        String token = authorization.substring(7);

        try {
            Claims claims = jwtProvider.validateToken(token);
            long id = Long.parseLong(claims.getSubject());

            request.setAttribute("memberId", id);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            handleException(response,INVALID_TOKEN.message());
        }
    }

    private void handleException(HttpServletResponse response, String message) throws IOException {
        Response errorResponse = new Response(StatusCode.UNAUTHORIZED.getStatusCode(), message);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
