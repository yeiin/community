package com.ktb.community.domain.auth;

import com.ktb.community.domain.BaseTime;
import com.ktb.community.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Auth extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true, nullable = false)
    private Member member;

    private String refreshTokenHash;

    @Builder
    public Auth(final Member member, final String refreshTokenHash) {
        this.member = member;
        this.refreshTokenHash = refreshTokenHash;
    }

    public void updateRefreshTokenHash(final String refreshTokenHash) {
        this.refreshTokenHash = refreshTokenHash;
    }
}
