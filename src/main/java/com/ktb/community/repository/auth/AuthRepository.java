package com.ktb.community.repository.auth;

import com.ktb.community.domain.auth.Auth;

import java.util.Optional;

public interface AuthRepository {
    Auth save(final Auth auth);
    Optional<Auth> findByMemberId(final long memberId);
    Optional<Auth> findByRefreshTokenHash(final String refreshTokenHash);
    void deleteByMemberId(final long memberId);
    void deleteByAuth(final Auth auth);
    void flush();
}
