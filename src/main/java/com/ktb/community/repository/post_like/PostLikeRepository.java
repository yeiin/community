package com.ktb.community.repository.post_like;

import com.ktb.community.domain.post_like.PostLike;

import java.util.Optional;

public interface PostLikeRepository {
    PostLike save(final PostLike postLike);
    Optional<PostLike> findByPostIdAndMemberId(final long postId, final long memberId);
    PostLike getByPostIdAndMemberId(final long postId, final long memberId);
    void deleteByPostLike(final PostLike postLike);
    void deleteAllByPostId(final long postId);
    void deleteAllByMemberId(final long memberId);
}
