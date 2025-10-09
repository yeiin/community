package com.ktb.community.domain.auth;

import com.ktb.community.domain.BaseTime;
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

    @Column(unique = true)
    private long memberId;

    private String refreshTokenHash;

    @Builder
    public Auth(final long memberId, final String refreshTokenHash) {
        this.memberId = memberId;
        this.refreshTokenHash = refreshTokenHash;
    }

    public void updateRefreshTokenHash(final String refreshTokenHash) {
        this.refreshTokenHash = refreshTokenHash;
    }
}
