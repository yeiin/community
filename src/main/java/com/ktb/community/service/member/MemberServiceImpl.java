package com.ktb.community.service.member;

import com.ktb.community.domain.member.Member;
import com.ktb.community.dto.Response;
import com.ktb.community.dto.member.request.MemberPatchRequest;
import com.ktb.community.dto.member.request.MemberPostRequest;
import com.ktb.community.dto.member.request.PasswordRequest;
import com.ktb.community.dto.auth.response.LoginResponse;
import com.ktb.community.dto.member.response.MemberResponse;
import com.ktb.community.global.encrypt.EncryptEncoder;
import com.ktb.community.global.exception.CustomConflictException;
import com.ktb.community.repository.member.MemberRepository;
import com.ktb.community.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ktb.community.global.constant.ExceptionConstant.EMAIL_CONFLICT;
import static com.ktb.community.global.constant.ExceptionConstant.NICKNAME_CONFLICT;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final EncryptEncoder encryptEncoder;
    private final AuthService authService;

    public MemberServiceImpl(final MemberRepository memberRepository, final EncryptEncoder encryptEncoder,
                             final AuthService authService) {
        this.memberRepository = memberRepository;
        this.encryptEncoder = encryptEncoder;
        this.authService = authService;
    }

    @Transactional
    @Override
    public LoginResponse save(final MemberPostRequest memberPostRequest){
        nicknameCheck(memberPostRequest.nickname());
        emailCheck(memberPostRequest.email());

        Member member = Member.builder()
                .email(memberPostRequest.email())
                .nickname(memberPostRequest.nickname())
                .password(encryptEncoder.bcryptEncrypt(memberPostRequest.password()))
                .state(true)
                .imageUrl(memberPostRequest.imageUrl())
                .build();

        memberRepository.save(member);
        return authService.createJwts(member.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Response nicknameValidate(final String nickname){
        nicknameCheck(nickname);
        return Response.of(HttpStatus.OK, "사용 가능한 닉네임입니다.");
    }

    @Transactional(readOnly = true)
    @Override
    public Response emailValidate(final String email){
        emailCheck(email);
        return Response.of(HttpStatus.OK, "사용 가능한 이메일입니다.");
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponse getMemberProfile(final long memberId) {
        Member member = memberRepository.getById(memberId);
        return MemberResponse.from(member);
    }

    @Transactional
    @Override
    public MemberResponse updateMemberProfile(final long memberId, final MemberPatchRequest memberPatchRequest) {

        nicknameCheck(memberId, memberPatchRequest.nickname());

        Member member = memberRepository.getById(memberId);
        member.updateNickname(memberPatchRequest.nickname());

        if(memberPatchRequest.imageUrl() != null) {
            member.updateImageUrl(memberPatchRequest.imageUrl());
        }

        return MemberResponse.from(member);
    }

    @Transactional
    @Override
    public Response updateMemberPassword(final long memberId, final PasswordRequest passwordRequest) {
        Member member = memberRepository.getById(memberId);
        member.updatePassword(encryptEncoder.bcryptEncrypt(passwordRequest.password()));

        return Response.of(HttpStatus.OK, "패스워드 변경에 성공했습니다.");
    }

    private void nicknameCheck(final String nickname) {
        if(memberRepository.existsNickname(nickname)) {
            throw new CustomConflictException(NICKNAME_CONFLICT);
        }
    }

    private void nicknameCheck(final long memberId, final String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if(member.isPresent() && member.get().getId() != memberId) {
            throw new CustomConflictException(NICKNAME_CONFLICT);
        }
    }

    private void emailCheck(final String email) {
        if(memberRepository.existsEmail(email)) {
            throw new CustomConflictException(EMAIL_CONFLICT);
        }
    }

    @Transactional
    @Override
    public Response softDeleteMember(final long memberId) {
        Member member = memberRepository.getById(memberId);
        member.updateState(false);
        authService.deleteAuthByMemberId(memberId);
        return Response.of(HttpStatus.OK, "회원 탈퇴에 성공했습니다.");
    }

}
