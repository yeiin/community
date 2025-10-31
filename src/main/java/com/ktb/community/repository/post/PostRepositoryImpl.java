package com.ktb.community.repository.post;

import com.ktb.community.domain.post.Post;
import com.ktb.community.global.exception.CustomNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ktb.community.global.constant.ExceptionConstant.POST_NOT_FOUND;

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
                .orElseThrow(()-> new CustomNotFoundException(POST_NOT_FOUND));
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
