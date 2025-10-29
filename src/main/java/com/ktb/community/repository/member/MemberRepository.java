package com.ktb.community.repository.member;

import com.ktb.community.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(final Member member);
    Member getById(final long id);
    Optional<Member> findByEmail(final String email);
    boolean existsNickname(String nickname);
    Optional<Member> findByNickname(String nickname);
    boolean existsEmail(String email);
}
