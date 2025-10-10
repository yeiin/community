package com.ktb.community.controller.post;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.post.request.PostRequestDto;
import com.ktb.community.dto.post.response.PostBasicDto;
import com.ktb.community.dto.post.response.PostDto;
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
    public PostBasicDto savePost(@RequestBody @Valid PostRequestDto postRequestDto, @Login long memberId){
        return postService.savePost(postRequestDto, memberId);
    }

    @GetMapping("")
    public PostResponses getPosts(@RequestParam(value = "lastSeenId", required = false) final Long lastSeenId){
        return postService.getPosts(lastSeenId);
    }

    @GetMapping("/{postId}")
    public PostDto getPost(@PathVariable("postId") final long postId){
        return postService.getPost(postId);
    }

    @PatchMapping("/{postId}")
    public PostBasicDto patchPost(@PathVariable("postId") final long postId, @Login long memberId,
                                  @RequestBody @Valid PostRequestDto postRequestDto){
        return postService.patchPost(postId, postRequestDto);
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
