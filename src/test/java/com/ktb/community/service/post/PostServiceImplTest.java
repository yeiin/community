package com.ktb.community.service.post;

import com.ktb.community.domain.post.Post;
import com.ktb.community.domain.post_like.PostLike;
import com.ktb.community.domain.post_stats.PostStats;
import com.ktb.community.dto.Response;
import com.ktb.community.repository.member.MemberRepository;
import com.ktb.community.repository.post.PostRepository;
import com.ktb.community.repository.post_like.PostLikeRepository;
import com.ktb.community.repository.post_stats.PostStatsRepository;
import com.ktb.community.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    @Mock
    PostLikeRepository postLikeRepository;

    @Mock
    PostStatsRepository postStatsRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    AuthService authService;

    @InjectMocks
    PostServiceImpl postService;

    @Test()
    @DisplayName("기존 좋아요 정보로 인해 저장에 실패한다.")
    void likePostFailTest() {
        //given
        long postId = 1L;
        long memberId = 1L;

        Post post = new Post(memberId, "title", "contents", "imageUrl");
        Optional<PostLike> optionalPostLike = Optional.of(new PostLike(postId, memberId));

        when(postRepository.getById(postId)).thenReturn(post);
        when(postLikeRepository.findByPostIdAndMemberId(postId, memberId)).thenReturn(optionalPostLike);

        //when //then
        assertThrows(ResponseStatusException.class, () -> postService.likePost(postId, memberId));
    }

    @Test
    @DisplayName("게시물에 대한 좋아요 저장에 성공한다.")
    void likePostTest() {
        //given
        long postId = 1L;
        long memberId = 1L;

        Post post = new Post(memberId, "title", "contents", "imageUrl");
        Optional<PostLike> optionalPostLike = Optional.empty();
        PostStats postStats = new PostStats(post);

        when(postRepository.getById(postId)).thenReturn(post);
        when(postLikeRepository.findByPostIdAndMemberId(postId, memberId)).thenReturn(optionalPostLike);
        when(postStatsRepository.getByPostId(postId)).thenReturn(postStats);

        //when
        Response response = postService.likePost(postId, memberId);

        // then
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        verify(postLikeRepository, times(1)).save(Mockito.any(PostLike.class));
        verify(postStatsRepository, times(1)).getByPostId(postId);
    }
}