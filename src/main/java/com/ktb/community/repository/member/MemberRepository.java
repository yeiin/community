package com.ktb.community.repository.member;

import com.ktb.community.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(final Member member);
    Optional<Member> findById(final long id);
    boolean existsNickname(String nickname);
    boolean existsEmail(String email);
}
