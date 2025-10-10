package com.ktb.community.service.post;

import com.ktb.community.domain.member.Member;
import com.ktb.community.domain.post.Post;
import com.ktb.community.domain.post_like.PostLike;
import com.ktb.community.domain.post_stats.PostStats;
import com.ktb.community.dto.Response;
import com.ktb.community.dto.post.request.PostRequestDto;
import com.ktb.community.dto.post.response.*;
import com.ktb.community.repository.member.MemberRepository;
import com.ktb.community.repository.post.PostRepository;
import com.ktb.community.repository.post_like.PostLikeRepository;
import com.ktb.community.repository.post_stats.PostStatsRepository;
import com.ktb.community.service.auth.AuthService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostStatsRepository postStatsRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    public PostServiceImpl(final PostRepository postRepository, final PostStatsRepository postStatsRepository,
                           final PostLikeRepository postLikeRepository, final MemberRepository memberRepository,
                           final AuthService authService) {
        this.postRepository = postRepository;
        this.postStatsRepository = postStatsRepository;
        this.postLikeRepository = postLikeRepository;
        this.memberRepository = memberRepository;
        this.authService = authService;
    }

    @Transactional
    @Override
    public PostBasicDto savePost(final PostRequestDto postRequestDto, final long memberId) {

        Post post = Post.builder()
                .memberId(memberId)
                .title(postRequestDto.title())
                .contents(postRequestDto.contents())
                .imageUrl(postRequestDto.imageUrl())
                .build();

        PostStats postStats = new PostStats(post);
        postStatsRepository.save(postStats);

        return PostBasicDto.from(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponses getPosts(final Long lastSeenId) {
        List<Post> postList = postRepository.getPostsForInfiniteScroll(lastSeenId, PageRequest.of(0, 10));
        List<PostDto> posts = postList.stream().map(this::fromPost).toList();

        return new PostResponses(posts);
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto getPost(final long postId) {
        Post post = postRepository.getById(postId);
        return fromPost(post);
    }

    private PostDto fromPost(final Post post){
        PostStats postStats = postStatsRepository.getByPostId(post.getId());
        postStats.incrementViewCount();

        Member member = memberRepository.getById(post.getMemberId());

        return PostDto.builder()
                .postBasic(PostBasicDto.from(post))
                .postCounter( PostCounterDto.from(postStats))
                .poster(PosterDto.from(member))
                .build();
    }

    @Transactional
    @Override
    public PostBasicDto patchPost(final long postId, final PostRequestDto postRequestDto) {
        Post post = postRepository.getById(postId);
        authService.checkAccountOwner(post.getMemberId());

        post.updateTitle(postRequestDto.title());
        post.updateContents(postRequestDto.contents());
        post.updateImageUrl(postRequestDto.imageUrl());

        return PostBasicDto.from(post);
    }

    @Transactional
    @Override
    public Response deletePost(final long postId) {
        Post post = postRepository.getById(postId);
        authService.checkAccountOwner(post.getMemberId());

        postRepository.delete(post);
        postLikeRepository.deleteAllByPostId(postId);

        return Response.of(HttpStatus.OK,"게시물 삭제에 성공했습니다.");
    }

    @Transactional
    @Override
    public Response likePost(final long postId, final long memberId) {
        Post post = postRepository.getById(postId);
        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndMemberId(postId, memberId);

        if (postLike.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 좋아요한 게시물입니다.");
        }

        postLikeRepository.save(new PostLike(postId, memberId));

        PostStats postStats = postStatsRepository.getByPostId(postId);
        postStats.incrementLikeCount();

        return Response.of(HttpStatus.CREATED, "게시물 좋아요에 성공했습니다.");
    }

    @Transactional
    @Override
    public Response unlikePost(final long postId, final long memberId) {
        PostLike postLike = postLikeRepository.getByPostIdAndMemberId(postId, memberId);
        postLikeRepository.deleteByPostLike(postLike);

        PostStats postStats = postStatsRepository.getByPostId(postId);
        postStats.decrementLikeCount();

        return Response.of(HttpStatus.OK, "게시물 좋아요 취소에 성공했습니다.");
    }
}
