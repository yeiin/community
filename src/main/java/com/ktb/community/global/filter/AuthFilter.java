package com.ktb.community.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.community.dto.Response;
import com.ktb.community.global.constant.StatusCode;
import com.ktb.community.global.provider.SessionProvider;
import com.ktb.community.global.validator.RouteValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ktb.community.global.constant.ExceptionConstant.INVALID_TOKEN;

@Slf4j
@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {

    private final RouteValidator routeValidator;
    private final ObjectMapper objectMapper;
    private final SessionProvider sessionProvider;

    public AuthFilter(final RouteValidator routeValidator, final ObjectMapper objectMapper,
                      final SessionProvider sessionProvider) {
        this.routeValidator = routeValidator;
        this.objectMapper = objectMapper;
        this.sessionProvider = sessionProvider;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return !routeValidator.isSecured.test(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Long id = sessionProvider.getLoginSession();

        if(id == null){
            handleException(response,INVALID_TOKEN.message());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, String message) throws IOException {
        Response errorResponse = new Response(StatusCode.UNAUTHORIZED.getStatusCode(), message);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
