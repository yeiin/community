package com.ktb.community.controller.post;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.post.request.PostRequest;
import com.ktb.community.dto.post.response.PostBasicResponse;
import com.ktb.community.dto.post.response.PostResponse;
import com.ktb.community.dto.post.response.PostResponses;
import com.ktb.community.global.annotation.Login;
import com.ktb.community.service.post.PostService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public PostBasicResponse savePost(@RequestBody @Valid PostRequest postRequest, @Login long memberId){
        return postService.savePost(postRequest, memberId);
    }

    @GetMapping("")
    public PostResponses getPosts(@RequestParam(value = "lastSeenId", required = false) final Long lastSeenId,
                                  @Login long memberId){
        return postService.getPosts(memberId, lastSeenId);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable("postId") final long postId, @Login long memberId){
        return postService.getPost(memberId, postId);
    }

    @PatchMapping("/{postId}")
    public PostBasicResponse patchPost(@PathVariable("postId") final long postId, @Login long memberId,
                                       @RequestBody @Valid PostRequest postRequest){
        return postService.patchPost(postId, postRequest);
    }

    @DeleteMapping("/{postId}")
    public Response deletePost(@PathVariable("postId") final long postId, @Login long memberId){
        return postService.deletePost(postId);
    }

    @PostMapping("/{postId}/like")
    public Response likePost(@PathVariable("postId") final long postId, @Login long memberId){
        return postService.likePost(postId, memberId);
    }

    @DeleteMapping("/{postId}/like")
    public Response unlikePost(@PathVariable("postId") final long postId, @Login long memberId){
        return postService.unlikePost(postId, memberId);
    }
}
