package com.ktb.community.domain.auth;

import com.ktb.community.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends BaseTime {

    @Id
    @MapsId
    @JoinColumn(name = "member_id")
    private long memberId;

    private String refreshTokenHash;

    public Auth(long memberId, String refreshTokenHash) {
        this.memberId = memberId;
        this.refreshTokenHash = refreshTokenHash;
    }
}
