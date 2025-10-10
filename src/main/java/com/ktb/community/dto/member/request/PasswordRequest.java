package com.ktb.community.dto.member.request;

import com.ktb.community.global.annotation.Password;

public record PasswordRequest(
        @Password
        String password
) {
}
