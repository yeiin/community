package com.ktb.community.dto.post.response;

import com.ktb.community.domain.post_stats.PostStats;
import lombok.Builder;

@Builder
public record PostCounterResponse(
        long likes,
        long comments,
        long views
) {

    public static PostCounterResponse from(PostStats postStats) {
        return PostCounterResponse.builder()
                .likes(postStats.getLikeCount())
                .comments(postStats.getCommentCount())
                .views(postStats.getViewCount())
                .build();
    }
}
