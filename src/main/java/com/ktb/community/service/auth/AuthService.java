package com.ktb.community.service.auth;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.auth.request.RefreshTokenRequest;
import com.ktb.community.dto.member.response.LoginResponse;

public interface AuthService {
    LoginResponse createJwts(final long memberId);
    LoginResponse login(final LoginRequest loginRequest);
    LoginResponse updateWithRefreshToken(final RefreshTokenRequest refreshTokenRequest);
    Response logout(final long memberId);
    boolean checkAccountOwner(final long memberId);
}
