package com.ktb.community.service.member;

import com.ktb.community.dto.member.request.MemberPostDto;
import com.ktb.community.dto.member.response.MemberDto;

public interface MemberService {
    MemberDto save(final MemberPostDto memberPostDto);
}
