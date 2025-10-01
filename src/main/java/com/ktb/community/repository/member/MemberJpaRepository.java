package com.ktb.community.repository.member;

import com.ktb.community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
