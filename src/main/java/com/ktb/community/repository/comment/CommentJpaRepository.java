package com.ktb.community.repository.comment;

import com.ktb.community.domain.comment.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.postId = :postId AND (:lastSeenId IS NULL OR c.id < :lastSeenId) order by c.id desc ")
    List<Comment> findListByPostIdAndIdLessThanOrderByIdDesc(final long postId, final Long lastSeenId, final Pageable pageable);
}
