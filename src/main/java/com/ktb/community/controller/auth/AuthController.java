package com.ktb.community.controller.auth;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.auth.request.RefreshTokenRequest;
import com.ktb.community.dto.member.response.LoginResponse;
import com.ktb.community.global.annotation.AccountOwner;
import com.ktb.community.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public LoginResponse login(@RequestBody final LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PatchMapping("")
    public LoginResponse updateWithRefreshToken(@RequestBody final RefreshTokenRequest refreshTokenRequest){
        return authService.updateWithRefreshToken(refreshTokenRequest);
    }

    @DeleteMapping("")
    @AccountOwner
    public Response logout(@RequestParam("memberId") final long memberId){
        return  authService.logout(memberId);
    }
}
