package com.ktb.community.dto.member.response;

public record LoginResponse(
        long userId,
        String accessToken,
        String refreshToken
) {
}
