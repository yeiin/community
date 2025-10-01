package com.ktb.community.controller.member;

import com.ktb.community.dto.member.request.MemberPostDto;
import com.ktb.community.dto.member.response.MemberDto;
import com.ktb.community.service.member.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public MemberDto signUp(final @RequestBody MemberPostDto memberPostDto) {
        return memberService.save(memberPostDto);
    }

}
