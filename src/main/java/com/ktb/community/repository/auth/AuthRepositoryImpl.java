package com.ktb.community.repository.auth;

import com.ktb.community.domain.auth.Auth;
import com.ktb.community.repository.member.MemberJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

    private AuthJpaRepository authJpaRepository;

    public AuthRepositoryImpl(AuthJpaRepository authJpaRepository, MemberJpaRepository memberJpaRepository) {
        this.authJpaRepository = authJpaRepository;
    }

    @Override
    public Auth save(final Auth auth) {
        return authJpaRepository.save(auth);
    }

    @Override
    public Optional<Auth> findByMemberId(final long memberId) {
        return authJpaRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<Auth> findByRefreshTokenHash(final String refreshTokenHash) {
        return authJpaRepository.findByRefreshTokenHash(refreshTokenHash);
    }

    @Override
    public void deleteByMemberId(final long memberId) {
        authJpaRepository.deleteByMemberId(memberId);
    }

    @Override
    public void deleteByAuth(Auth auth) {
        authJpaRepository.delete(auth);
    }

    @Override
    public void flush(){
        authJpaRepository.flush();
    }
}
