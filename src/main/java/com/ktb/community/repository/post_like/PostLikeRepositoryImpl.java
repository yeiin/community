package com.ktb.community.repository.post_like;

import com.ktb.community.domain.post_like.PostLike;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public class PostLikeRepositoryImpl implements PostLikeRepository {

    private final PostLikeJpaRepository postLikeJpaRepository;

    public PostLikeRepositoryImpl(final PostLikeJpaRepository postLikeJpaRepository) {
        this.postLikeJpaRepository = postLikeJpaRepository;
    }


    @Override
    public PostLike save(final PostLike postLike) {
        return postLikeJpaRepository.save(postLike);
    }

    @Override
    public Optional<PostLike> findByPostIdAndMemberId(final long postId, final long memberId) {
        return postLikeJpaRepository.findByPostIdAndMemberId(postId, memberId);
    }

    @Override
    public PostLike getByPostIdAndMemberId(long postId, long memberId) {
        return postLikeJpaRepository.findByPostIdAndMemberId(postId, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물의 좋아요 정보를 찾을 수 없습니다."));
    }

    @Override
    public void deleteByPostLike(final PostLike postLike) {
        postLikeJpaRepository.delete(postLike);
    }

    @Override
    public void deleteAllByPostId(final long postId) {
        postLikeJpaRepository.deleteAllByPostId(postId);
    }

    @Override
    public void deleteAllByMemberId(final long memberId) {
        postLikeJpaRepository.deleteAllByMemberId(memberId);
    }
}
