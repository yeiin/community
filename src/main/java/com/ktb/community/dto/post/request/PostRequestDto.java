package com.ktb.community.dto.post.request;

import com.ktb.community.global.annotation.PostTitle;

public record PostRequestDto(
        @PostTitle
        String title,
        String contents,
        String imageUrl
) {
}
