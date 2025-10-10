package com.ktb.community.service.post;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.post.request.PostRequestDto;
import com.ktb.community.dto.post.response.PostBasicDto;
import com.ktb.community.dto.post.response.PostDto;
import com.ktb.community.dto.post.response.PostResponses;

public interface PostService {
    PostBasicDto savePost(final PostRequestDto postRequestDto, final long memberId);
    PostResponses getPosts(final Long lastSeenId);
    PostDto getPost(final long postId);
    PostBasicDto patchPost(final long postId, final PostRequestDto postRequestDto);
    Response deletePost(final long postId);
    Response likePost(final long postId, final long memberId);
    Response unlikePost(final long postId, final long memberId);
}
