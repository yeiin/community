package com.ktb.community.repository.post_like;

import com.ktb.community.domain.post_like.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeJpaRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndMemberId(final long postId, final long memberId);
    void deleteAllByPostId(final long postId);
    void deleteAllByMemberId(final long memberId);
}
