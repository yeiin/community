package com.ktb.community.service.jwt;

import com.ktb.community.dto.member.response.LoginResponse;

public interface JwtService {
    LoginResponse createJwts(final long memberId);

}
