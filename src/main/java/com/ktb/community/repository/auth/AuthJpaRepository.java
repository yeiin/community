package com.ktb.community.repository.auth;

import com.ktb.community.domain.auth.Auth;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthJpaRepository extends JpaRepository<Auth, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Auth a where a.memberId = :memberId")
    Optional<Auth> findByMemberId(final long memberId);
    Optional<Auth> findByRefreshTokenHash(final String refreshTokenHash);
}
