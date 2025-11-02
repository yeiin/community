package com.ktb.community.service.auth;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.member.response.MemberResponse;

public interface AuthService {
    MemberResponse login(final LoginRequest loginRequest);
    Response logout(final long memberId);
    boolean checkAccountOwner(final long memberId);
}
