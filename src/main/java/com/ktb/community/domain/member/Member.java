package com.ktb.community.domain.member;

import com.ktb.community.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String imageUrl;
    private boolean state;

    @Builder
    public Member(String email, String nickname, String password, String imageUrl, boolean state) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.imageUrl = imageUrl;
        this.state = state;
    }
}
