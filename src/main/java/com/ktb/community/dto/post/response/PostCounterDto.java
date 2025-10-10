package com.ktb.community.dto.post.response;

import com.ktb.community.domain.post_stats.PostStats;
import lombok.Builder;

@Builder
public record PostCounterDto(
        long likes,
        long comments,
        long views
) {

    public static PostCounterDto from(PostStats postStats) {
        return PostCounterDto.builder()
                .likes(postStats.getLikeCount())
                .comments(postStats.getCommentCount())
                .views(postStats.getViewCount())
                .build();
    }
}
