package com.ktb.community.repository.member;

import com.ktb.community.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findById(final long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsEmail(String email) {
        return memberJpaRepository.existsByEmail(email);
    }

}
