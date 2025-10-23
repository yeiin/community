package com.ktb.community.domain.post_stats;

import com.ktb.community.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_stats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostStats {

    @Id
    @Column(name = "post_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "post_id")
    private Post post;

    private long likeCount;
    private long commentCount;
    private long viewCount;

    public PostStats(Post post) {
        this.post = post;
        this.likeCount = 0;
        this.commentCount = 0;
        this.viewCount = 0;
    }

    public void incrementLikeCount() {
        likeCount++;
    }

    public void decrementLikeCount() {
        likeCount--;
    }

    public void incrementCommentCount() {
        commentCount++;
    }

    public void decrementCommentCount() {
        commentCount--;
    }

    public void incrementViewCount() {
        viewCount++;
    }
}
