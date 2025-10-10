package com.ktb.community.repository.comment;

import com.ktb.community.domain.comment.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    public CommentRepositoryImpl(final CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }

    @Override
    public Comment save(final Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Comment getById(long id) {
        return commentJpaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
    }

    @Override
    public List<Comment> findAllByPostIdForInfiniteScroll(final long postId, final Long lastSeenId, final Pageable pageable) {
        return commentJpaRepository.findListByPostIdAndIdLessThanOrderByIdDesc(postId, lastSeenId, pageable);
    }

    @Override
    public void delete(final Comment comment) {
        commentJpaRepository.delete(comment);
    }
}
