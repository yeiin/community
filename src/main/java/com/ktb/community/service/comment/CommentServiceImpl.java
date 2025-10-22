package com.ktb.community.service.comment;

import com.ktb.community.domain.comment.Comment;
import com.ktb.community.domain.post.Post;
import com.ktb.community.domain.post_stats.PostStats;
import com.ktb.community.dto.Response;
import com.ktb.community.dto.comment.request.CommentRequest;
import com.ktb.community.dto.comment.response.CommentBasicResponse;
import com.ktb.community.dto.comment.response.CommentResponse;
import com.ktb.community.dto.comment.response.CommentResponses;
import com.ktb.community.dto.post.response.PosterResponse;
import com.ktb.community.repository.comment.CommentRepository;
import com.ktb.community.repository.member.MemberRepository;
import com.ktb.community.repository.post.PostRepository;
import com.ktb.community.repository.post_stats.PostStatsRepository;
import com.ktb.community.service.auth.AuthService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostStatsRepository postStatsRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    public CommentServiceImpl(final CommentRepository commentRepository, final PostRepository postRepository,
                              final PostStatsRepository postStatsRepository, final MemberRepository memberRepository,
                              final AuthService authService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postStatsRepository = postStatsRepository;
        this.memberRepository = memberRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    @Override
    public CommentResponses getCommentsByPostId(final long postId, final Long lastSeenId) {

        List<Comment> commentList = commentRepository.findAllByPostIdForInfiniteScroll(
                postId,
                lastSeenId,
                PageRequest.of(0, 10));


        List<CommentResponse> comments = commentList.stream()
                .map(c -> {
                    CommentBasicResponse commentBasicResponse = CommentBasicResponse.from(c);
                    PosterResponse posterResponse = PosterResponse.from(memberRepository.getById(c.getMemberId()));
                    return new CommentResponse(commentBasicResponse, posterResponse);})
                .toList();

        return new CommentResponses(comments);
    }

    @Transactional
    @Override
    public CommentBasicResponse saveCommentByPostId(final long postId, final long memberId,
                                                    final CommentRequest commentRequest) {

        Post post = postRepository.getById(postId);

        Comment comment = new Comment(postId, memberId, commentRequest.contents());
        commentRepository.save(comment);

        PostStats postStats = postStatsRepository.getByPostId(postId);
        postStats.incrementCommentCount();

        return CommentBasicResponse.from(comment);
    }

    @Transactional
    @Override
    public CommentBasicResponse patchCommentByPostId(final long postId, final long commentId,
                                                     final CommentRequest commentRequest) {
        Post post = postRepository.getById(postId);

        Comment comment = commentRepository.getById(commentId);
        authService.checkAccountOwner(comment.getMemberId());

        comment.updateContents(commentRequest.contents());

        return CommentBasicResponse.from(comment);
    }

    @Transactional
    @Override
    public Response deleteComment(final long postId, final long commentId) {
        Post post = postRepository.getById(postId);

        Comment comment = commentRepository.getById(commentId);
        authService.checkAccountOwner(comment.getMemberId());

        commentRepository.delete(comment);
        return Response.of(HttpStatus.OK, "댓글 삭제에 성공했습니다.");
    }
}
