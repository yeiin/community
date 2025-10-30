package com.ktb.community.service.member;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.member.request.MemberPatchRequest;
import com.ktb.community.dto.member.request.MemberPostRequest;
import com.ktb.community.dto.member.request.PasswordRequest;
import com.ktb.community.dto.auth.response.LoginResponse;
import com.ktb.community.dto.member.response.MemberResponse;

public interface MemberService {
    LoginResponse save(final MemberPostRequest memberPostRequest);
    Response nicknameValidate(final String nickname);
    Response emailValidate(final String email);
    MemberResponse getMemberProfile(final long memberId);
    MemberResponse updateMemberProfile(final long memberId, final MemberPatchRequest memberPatchRequest);
    Response updateMemberPassword(final long memberId, final PasswordRequest passwordRequest);
    Response softDeleteMember(final long memberId);
}
