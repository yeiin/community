package com.ktb.community.domain.post;

import com.ktb.community.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long memberId;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String contents;

    private String imageUrl;

    @Builder
    public Post(final long memberId, final String title, final String contents, final String imageUrl) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.imageUrl = imageUrl;
    }

    public void updateTitle(final String title) {
        this.title = title;
    }

    public void updateContents(final String contents) {
        this.contents = contents;
    }

    public void updateImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
