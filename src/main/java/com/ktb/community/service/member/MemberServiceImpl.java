package com.ktb.community.service.member;

import com.ktb.community.domain.member.Member;
import com.ktb.community.dto.Response;
import com.ktb.community.dto.member.request.MemberPatchDto;
import com.ktb.community.dto.member.request.MemberPostDto;
import com.ktb.community.dto.member.request.PasswordPatchDto;
import com.ktb.community.dto.member.response.LoginResponse;
import com.ktb.community.dto.member.response.MemberDto;
import com.ktb.community.global.encrypt.EncryptEncoder;
import com.ktb.community.repository.member.MemberRepository;
import com.ktb.community.service.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final EncryptEncoder encryptEncoder;
    private final JwtService jwtService;

    public MemberServiceImpl(final MemberRepository memberRepository, final EncryptEncoder encryptEncoder,
                             final JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.encryptEncoder = encryptEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public LoginResponse save(final MemberPostDto memberPostDto){
        nicknameCheck(memberPostDto.nickname());
        emailCheck(memberPostDto.email());

        Member member = Member.builder()
                .email(memberPostDto.email())
                .nickname(memberPostDto.nickname())
                .password(encryptEncoder.encrypt(memberPostDto.password()))
                .state(true)
                .imageUrl(memberPostDto.imageUrl())
                .build();

        memberRepository.save(member);
        return jwtService.createJwts(member.getId());
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
    public MemberDto getMemberProfile(final long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        return MemberDto.from(member);
    }

    @Transactional
    @Override
    public MemberDto updateMemberProfile(final long memberId, final MemberPatchDto memberPatchDto) {
        nicknameCheck(memberPatchDto.nickname());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        member.updateNickname(memberPatchDto.nickname());
        if(memberPatchDto.imageUrl() != null) {
            member.updateImageUrl(memberPatchDto.imageUrl());
        }

        return MemberDto.from(member);
    }

    @Transactional
    @Override
    public Response updateMemberPassword(final long memberId, final PasswordPatchDto passwordPatchDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        member.updatePassword(encryptEncoder.encrypt(passwordPatchDto.password()));

        return Response.of(HttpStatus.OK, "패스워드 변경에 성공했습니다.");
    }

    private void nicknameCheck(final String nickname) {
        if(memberRepository.existsNickname(nickname)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다.");
        }
    }

    private void emailCheck(final String email) {
        if(memberRepository.existsEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }
    }

    @Transactional
    @Override
    public Response softDeleteMember(final long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        member.updateState(false);

        return Response.of(HttpStatus.OK, "회원 탈퇴에 성공했습니다.");
    }

}
