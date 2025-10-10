package com.ktb.community.repository.member;

import com.ktb.community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(final String email);
    boolean existsByNickname(final String nickname);
    boolean existsByEmail(final String email);
}
