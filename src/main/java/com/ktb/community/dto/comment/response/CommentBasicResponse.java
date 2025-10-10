package com.ktb.community.dto.comment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ktb.community.domain.comment.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentBasicResponse(
        long commentId,
        String contents,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

    public static CommentBasicResponse from(final Comment comment) {
        return CommentBasicResponse.builder()
                .commentId(comment.getId())
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
