package com.ktb.community.dto.post.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ktb.community.domain.post.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostBasicResponse(
        long id,
        long memberId,
        String title,
        String contents,
        String imageUrl,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

        public static PostBasicResponse from(final Post post) {
                return PostBasicResponse.builder()
                        .id(post.getId())
                        .memberId(post.getMemberId())
                        .title(post.getTitle())
                        .contents(post.getContents())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .build();
        }
}
