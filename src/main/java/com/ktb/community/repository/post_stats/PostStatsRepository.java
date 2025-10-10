package com.ktb.community.repository.post_stats;

import com.ktb.community.domain.post_stats.PostStats;

public interface PostStatsRepository {
    PostStats save(final PostStats postStats);
    PostStats getByPostId(final long postId);
}
