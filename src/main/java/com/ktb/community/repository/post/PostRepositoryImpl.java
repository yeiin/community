package com.ktb.community.repository.post;

import com.ktb.community.domain.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;

    public PostRepositoryImpl(final PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    @Override
    public Post save(final Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public Post getById(final long postId) {
        return postJpaRepository.findById(postId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));
    }

    @Override
    public List<Post> getPostsForInfiniteScroll(final Long lastSeenId, final Pageable pageable) {
        return postJpaRepository.findSliceByIdLessThanOrderByIdDesc(lastSeenId, pageable);
    }

    @Override
    public void delete(final Post post) {
        postJpaRepository.delete(post);
    }
}
