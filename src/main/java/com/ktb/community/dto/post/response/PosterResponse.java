package com.ktb.community.dto.post.response;

import com.ktb.community.domain.member.Member;
import lombok.Builder;

@Builder
public record PosterResponse(
        long memberId,
        String nickname,
        String imageUrl,
        boolean isMe
) {

    public static PosterResponse from(Member member, long loginId) {
        return PosterResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .isMe(loginId == member.getId())
                .build();
    }
}
