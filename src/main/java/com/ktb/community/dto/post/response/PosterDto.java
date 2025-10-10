package com.ktb.community.dto.post.response;

import com.ktb.community.domain.member.Member;
import lombok.Builder;

@Builder
public record PosterDto(
        long memberId,
        String nickname,
        String imageUrl
) {

    public static PosterDto from(Member member) {
        return PosterDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
