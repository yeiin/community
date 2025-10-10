package com.ktb.community.repository.post;

import com.ktb.community.domain.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where (:lastSeenId is null or p.id < :lastSeenId) order by p.id desc ")
    List<Post> findSliceByIdLessThanOrderByIdDesc(final Long lastSeenId, final Pageable pageable);
}
