package com.ktb.community.controller.comment;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.comment.request.CommentRequest;
import com.ktb.community.dto.comment.response.CommentBasicResponse;
import com.ktb.community.dto.comment.response.CommentResponses;
import com.ktb.community.global.annotation.Login;
import com.ktb.community.service.comment.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public CommentResponses getComment(@PathVariable long postId,
                                       @RequestParam(value = "lastSeenId", required = false) Long lastSeenId) {
        return commentService.getCommentsByPostId(postId, lastSeenId);
    }

    @PostMapping("")
    public CommentBasicResponse postComment(@PathVariable long postId, @Login long memberId,
                                            @RequestBody CommentRequest commentRequest) {
        return commentService.saveCommentByPostId(postId, memberId, commentRequest);
    }

    @PatchMapping("/{commentId}")
    public CommentBasicResponse patchComment(@PathVariable long postId, @PathVariable long commentId,
                                @RequestBody CommentRequest commentRequest) {
        return commentService.patchCommentByPostId(postId, commentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public Response deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        return commentService.deleteComment(postId, commentId);
    }
}
