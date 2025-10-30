package com.ktb.community.controller.auth;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.auth.request.RefreshTokenRequest;
import com.ktb.community.dto.auth.response.LoginResponse;
import com.ktb.community.global.annotation.AccountOwner;
import com.ktb.community.global.provider.CookieProvider;
import com.ktb.community.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieProvider cookieProvider;

    public AuthController(final AuthService authService, final CookieProvider cookieProvider) {
        this.authService = authService;
        this.cookieProvider = cookieProvider;
    }

    @PostMapping("")
    public LoginResponse login(@RequestBody final LoginRequest loginRequest,
                                               final HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(loginRequest);
        cookieProvider.setRefreshTokenCookie(response,loginResponse.refreshToken());
        return loginResponse;
    }

    @PatchMapping("")
    public LoginResponse updateWithRefreshToken(@RequestBody final RefreshTokenRequest refreshTokenRequest,
                                                                final HttpServletResponse response){
        LoginResponse loginResponse = authService.updateWithRefreshToken(refreshTokenRequest);
        cookieProvider.setRefreshTokenCookie(response,loginResponse.refreshToken());
        return loginResponse;
    }

    @DeleteMapping("")
    @AccountOwner
    public Response logout(@RequestParam("memberId") final long memberId){
        return authService.logout(memberId);
    }
}
