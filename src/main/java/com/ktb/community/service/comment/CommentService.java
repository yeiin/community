package com.ktb.community.service.comment;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.comment.request.CommentRequest;
import com.ktb.community.dto.comment.response.CommentBasicResponse;
import com.ktb.community.dto.comment.response.CommentResponses;

public interface CommentService {
    CommentResponses getCommentsByPostId(final long postId, final long memberId, final Long lastSeenId);
    CommentBasicResponse saveCommentByPostId(final long postId, final long memberId, final CommentRequest commentRequest);
    CommentBasicResponse patchCommentByPostId(final long postId, final long commentId, final CommentRequest commentRequest);
    Response deleteComment(final long postId, final long commentId);
}
