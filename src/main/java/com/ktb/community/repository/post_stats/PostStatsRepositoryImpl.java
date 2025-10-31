package com.ktb.community.repository.post_stats;

import com.ktb.community.domain.post_stats.PostStats;
import com.ktb.community.global.exception.CustomNotFoundException;
import org.springframework.stereotype.Repository;

import static com.ktb.community.global.constant.ExceptionConstant.POST_STATS_NOT_FOUND;

@Repository
public class PostStatsRepositoryImpl implements PostStatsRepository {

    private final PostStatsJpaRepository postStatsJpaRepository;

    public PostStatsRepositoryImpl(final PostStatsJpaRepository postStatsJpaRepository) {
        this.postStatsJpaRepository = postStatsJpaRepository;
    }

    @Override
    public PostStats save(final PostStats postStats) {
        return postStatsJpaRepository.save(postStats);
    }

    @Override
    public PostStats getByPostId(final long postId) {
        return postStatsJpaRepository.findById(postId)
                .orElseThrow(()-> new CustomNotFoundException(POST_STATS_NOT_FOUND));
    }
}
