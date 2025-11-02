package com.ktb.community.controller.auth;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.member.response.MemberResponse;
import com.ktb.community.global.annotation.AccountOwner;
import com.ktb.community.global.provider.SessionProvider;
import com.ktb.community.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SessionProvider sessionProvider;

    public AuthController(final AuthService authService, final SessionProvider sessionProvider) {
        this.authService = authService;
        this.sessionProvider = sessionProvider;
    }

    @PostMapping("")
    public MemberResponse login(@RequestBody final LoginRequest loginRequest, final HttpServletResponse response) {
        MemberResponse memberResponse = authService.login(loginRequest);
        sessionProvider.setLoginSession(memberResponse.id());
        return memberResponse;
    }

    @DeleteMapping("")
    @AccountOwner(memberId = "#memberId")
    public Response logout(@RequestParam("memberId") final long memberId){
        sessionProvider.removeSession();
        return authService.logout(memberId);
    }
}
