package com.ktb.community.dto.post.response;

import com.ktb.community.domain.member.Member;
import lombok.Builder;

@Builder
public record PosterResponse(
        long memberId,
        String nickname,
        String imageUrl
) {

    public static PosterResponse from(Member member) {
        return PosterResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
