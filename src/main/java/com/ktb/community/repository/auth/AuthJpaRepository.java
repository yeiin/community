package com.ktb.community.repository.auth;

import com.ktb.community.domain.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthJpaRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByMemberId(final long memberId);
    Optional<Auth> findByRefreshTokenHash(final String refreshTokenHash);
    void deleteByMemberId(final long memberId);
}
