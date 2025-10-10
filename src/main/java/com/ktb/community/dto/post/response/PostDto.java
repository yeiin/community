package com.ktb.community.dto.post.response;

import lombok.Builder;

@Builder
public record PostDto(
        PostBasicDto postBasic,
        PostCounterDto postCounter,
        PosterDto poster
) {
}
