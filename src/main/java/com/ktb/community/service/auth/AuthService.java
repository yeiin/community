package com.ktb.community.service.auth;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.auth.response.LoginResponse;

public interface AuthService {
    LoginResponse createJwts(final long memberId);
    LoginResponse login(final LoginRequest loginRequest);
    LoginResponse updateWithRefreshToken(final String refreshToken);
    Response logout(final long memberId);
    void deleteAuthByMemberId(final long memberId);
    boolean checkAccountOwner(final long memberId);
}
