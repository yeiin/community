package com.ktb.community.domain.comment;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    private long postId;
    private long memberId;
    private String contents;

    public Comment(long postId, long memberId, String contents) {
        this.postId = postId;
        this.memberId = memberId;
        this.contents = contents;
    }
}
