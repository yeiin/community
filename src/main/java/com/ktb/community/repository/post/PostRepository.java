package com.ktb.community.repository.post;

import com.ktb.community.domain.post.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository {
    Post save(final Post post);
    Post getById(final long postId);
    List<Post> getPostsForInfiniteScroll(final Long lastSeenId, final Pageable pageable);
    void delete(final Post post);
}
