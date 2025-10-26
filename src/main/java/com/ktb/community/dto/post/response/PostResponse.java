package com.ktb.community.dto.post.response;

import lombok.Builder;

@Builder
public record PostResponse(
        PostBasicResponse postBasic,
        PostCounterResponse postCounter,
        PosterResponse poster,
        boolean isLike
) {
}
