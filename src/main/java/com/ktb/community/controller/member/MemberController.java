package com.ktb.community.controller.member;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.member.request.MemberPatchDto;
import com.ktb.community.dto.member.request.MemberPostDto;
import com.ktb.community.dto.member.request.PasswordPatchDto;
import com.ktb.community.dto.member.response.LoginResponse;
import com.ktb.community.dto.member.response.MemberDto;
import com.ktb.community.global.annotation.AccountOwner;
import com.ktb.community.global.annotation.Nickname;
import com.ktb.community.service.member.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/members"))
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public LoginResponse signUp(final @RequestBody @Valid MemberPostDto memberPostDto) {
        return memberService.save(memberPostDto);
    }

    @GetMapping("/nickname-validation")
    public Response nicknameValidation(@RequestParam("nickname") @Valid @Nickname final String nickname) {
        return memberService.nicknameValidate(nickname);
    }

    @GetMapping("/email-validation")
    public Response emailValidation(@RequestParam("email")
                                        @Valid @Email(message = "유효한 이메일 형식이 아닙니다.") final String email) {
        return memberService.emailValidate(email);
    }

    @GetMapping("/{memberId}/profile")
    public MemberDto getMemberProfile(@PathVariable("memberId") final long memberId) {
        return memberService.getMemberProfile(memberId);
    }

    @PatchMapping("/{memberId}/profile")
    @AccountOwner
    public MemberDto patchMemberProfile(@PathVariable("memberId") final long memberId,
                                        @RequestBody @Valid final MemberPatchDto memberPatchDto) {
        return memberService.updateMemberProfile(memberId, memberPatchDto);
    }

    @PatchMapping("/{memberId}/password")
    @AccountOwner
    public Response patchMemberPassword(@PathVariable("memberId") final long memberId,
                                    @RequestBody @Valid final PasswordPatchDto passwordPatchDto) {
        return memberService.updateMemberPassword(memberId, passwordPatchDto);
    }

    @PatchMapping("/{memberId}")
    @AccountOwner
    public Response softDeleteMember(@PathVariable("memberId") final long memberId) {
        return memberService.softDeleteMember(memberId);
    }

}
