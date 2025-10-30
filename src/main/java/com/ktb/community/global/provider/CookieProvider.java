package com.ktb.community.global.provider;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    @Value("${spring.jwt.refresh_exp_time}")
    private long refreshExpTime;

    public void setRefreshTokenCookie(HttpServletResponse response, String token) {
        int maxAge = (int)(refreshExpTime / 1000);

        String cookieValue = String.format(
                "%s=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=None",
                "refreshToken", token, maxAge
        );

        response.addHeader("Set-Cookie", cookieValue);
    }
}
