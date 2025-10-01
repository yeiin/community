package com.ktb.community.dto.member.response;

import com.ktb.community.domain.member.Member;
import lombok.Builder;

@Builder
public record MemberDto(
        long id,
        String email,
        String nickname,
        String imageUrl
) {

    public static MemberDto from(Member member) {
        return  MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
