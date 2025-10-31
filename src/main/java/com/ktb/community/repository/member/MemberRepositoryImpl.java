package com.ktb.community.repository.member;

import com.ktb.community.domain.member.Member;
import com.ktb.community.global.exception.CustomNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ktb.community.global.constant.ExceptionConstant.MEMBER_NOT_FOUND;

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
    public Member getById(final long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(MEMBER_NOT_FOUND));
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<Member> findByNickname(String nickname){
        return memberJpaRepository.findByNickname(nickname);
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
