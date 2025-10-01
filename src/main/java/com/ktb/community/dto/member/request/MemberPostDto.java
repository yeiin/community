package com.ktb.community.dto.member.request;

import com.ktb.community.global.annotation.Nickname;
import com.ktb.community.global.annotation.Password;
import jakarta.validation.constraints.Email;

public record MemberPostDto(

        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String email,

        @Nickname
        String nickname,

        @Password
        String password,

        String imageUrl
) {

}
