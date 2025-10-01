package com.ktb.community.domain.post_like;

import com.ktb.community.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_post_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_post_like_id")
    private long id;

    private long postId;
    private long memberId;

    public PostLike(long postId, long memberId) {
        this.postId = postId;
        this.memberId = memberId;
    }
}
