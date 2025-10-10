package com.ktb.community.dto.member.request;

import com.ktb.community.global.annotation.Nickname;

public record MemberPatchRequest(
        @Nickname
        String nickname,

        String imageUrl
) {
}
