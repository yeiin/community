package com.ktb.community.service.member;

import com.ktb.community.domain.member.Member;
import com.ktb.community.dto.member.request.MemberPostDto;
import com.ktb.community.dto.member.response.MemberDto;
import com.ktb.community.global.encrypt.EncryptEncoder;
import com.ktb.community.repository.member.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final EncryptEncoder encryptEncoder;

    public MemberServiceImpl(final MemberRepository memberRepository, final EncryptEncoder encryptEncoder) {
        this.memberRepository = memberRepository;
        this.encryptEncoder = encryptEncoder;
    }

    @Override
    public MemberDto save(final MemberPostDto memberPostDto){
        nicknameCheck(memberPostDto.nickname());
        emailCheck(memberPostDto.email());

        Member member = Member.builder()
                .email(memberPostDto.email())
                .nickname(memberPostDto.nickname())
                .password(encryptEncoder.encrypt(memberPostDto.password()))
                .state(true)
                .imageUrl(memberPostDto.imageUrl())
                .build();

        return MemberDto.from(memberRepository.save(member));
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

}
