package com.ktb.community.repository.comment;

import com.ktb.community.domain.comment.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepository {
    Comment save(final Comment comment);
    Comment getById(final long id);
    List<Comment> findAllByPostIdForInfiniteScroll(final long postId, final Long lastSeenId, final Pageable pageable);
    void delete(final Comment comment);
}
