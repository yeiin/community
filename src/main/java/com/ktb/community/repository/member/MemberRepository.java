package com.ktb.community.repository.member;

import com.ktb.community.domain.member.Member;

public interface MemberRepository {
    Member save(final Member member);
    boolean existsNickname(String nickname);
    boolean existsEmail(String email);
}
