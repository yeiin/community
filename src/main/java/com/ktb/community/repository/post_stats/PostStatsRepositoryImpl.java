package com.ktb.community.repository.post_stats;

import com.ktb.community.domain.post_stats.PostStats;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물 집계 정보를 찾을 수 없습니다."));
    }
}
