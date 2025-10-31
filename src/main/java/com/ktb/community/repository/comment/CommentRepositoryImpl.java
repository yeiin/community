package com.ktb.community.repository.comment;

import com.ktb.community.domain.comment.Comment;
import com.ktb.community.global.exception.CustomNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ktb.community.global.constant.ExceptionConstant.COMMENT_NOT_FOUND;

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
                .orElseThrow(()-> new CustomNotFoundException(COMMENT_NOT_FOUND));
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
