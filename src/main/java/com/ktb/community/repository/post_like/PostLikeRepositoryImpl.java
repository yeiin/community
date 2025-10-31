package com.ktb.community.repository.post_like;

import com.ktb.community.domain.post_like.PostLike;
import com.ktb.community.global.exception.CustomNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ktb.community.global.constant.ExceptionConstant.POST_LIKE_NOT_FOUND;

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
                .orElseThrow(() -> new CustomNotFoundException(POST_LIKE_NOT_FOUND));
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
