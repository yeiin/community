package com.ktb.community.service.member;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.member.request.MemberPatchDto;
import com.ktb.community.dto.member.request.MemberPostDto;
import com.ktb.community.dto.member.request.PasswordPatchDto;
import com.ktb.community.dto.member.response.LoginResponse;
import com.ktb.community.dto.member.response.MemberDto;

public interface MemberService {
    LoginResponse save(final MemberPostDto memberPostDto);
    Response nicknameValidate(final String nickname);
    Response emailValidate(final String email);
    MemberDto getMemberProfile(final long memberId);
    MemberDto updateMemberProfile(final long memberId, final MemberPatchDto memberPatchDto);
    Response updateMemberPassword(final long memberId, final PasswordPatchDto passwordPatchDto);
    Response softDeleteMember(final long memberId);
}
