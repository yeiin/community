package com.ktb.community.dto.post.response;

import com.ktb.community.domain.post_stats.PostStats;
import com.ktb.community.global.processor.NumberProcessor;
import lombok.Builder;

@Builder
public record PostCounterResponse(
        String likes,
        String comments,
        String views
) {

    public static PostCounterResponse from(PostStats postStats) {
        return PostCounterResponse.builder()
                .likes(NumberProcessor.getFormatNumber(postStats.getLikeCount()))
                .comments(NumberProcessor.getFormatNumber(postStats.getCommentCount()))
                .views(NumberProcessor.getFormatNumber(postStats.getViewCount()))
                .build();
    }

}
