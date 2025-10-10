package com.ktb.community.dto.comment.response;

import com.ktb.community.dto.post.response.PosterResponse;

public record CommentResponse(
        CommentBasicResponse commentBasic,
        PosterResponse poster
) {
}
