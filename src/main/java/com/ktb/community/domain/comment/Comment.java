package com.ktb.community.domain.comment;

import com.ktb.community.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    private long postId;

    private long memberId;

    private String contents;

    @Builder
    public Comment(final long postId, final long memberId, final String contents) {
        this.postId = postId;
        this.memberId = memberId;
        this.contents = contents;
    }

    public void updateContents(final String contents) {
        this.contents = contents;
    }
}
