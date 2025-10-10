package com.ktb.community.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.community.dto.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Response.of(HttpStatus.UNAUTHORIZED,"JWT 인증이 필요합니다." )));
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 인증이 필요합니다.");
    }
}
