package com.ktb.community.service.post;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.post.request.PostRequest;
import com.ktb.community.dto.post.response.PostBasicResponse;
import com.ktb.community.dto.post.response.PostResponse;
import com.ktb.community.dto.post.response.PostResponses;

public interface PostService {
    PostBasicResponse savePost(final PostRequest postRequest, final long memberId);
    PostResponses getPosts(final long memberId, final Long lastSeenId);
    PostResponse getPost(final long memberId, final long postId);
    PostBasicResponse patchPost(final long postId, final PostRequest postRequest);
    Response deletePost(final long postId);
    Response likePost(final long postId, final long memberId);
    Response unlikePost(final long postId, final long memberId);
}
