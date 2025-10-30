package com.ktb.community.dto.auth.response;

public record LoginResponse(
        long userId,
        String accessToken,
        String refreshToken
) {
}
