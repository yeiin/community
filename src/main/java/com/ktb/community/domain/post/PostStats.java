package com.ktb.community.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_stats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostStats {

    @Id
    @MapsId
    @Column(name = "post_id")
    private long postId;

    private long likeCount;
    private long commentCount;
    private long viewCount;

    public PostStats(long postId, long likeCount, long commentCount, long viewCount) {
        this.postId = postId;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
    }
}
