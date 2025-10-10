package com.ktb.community.controller.member;

import com.ktb.community.dto.Response;
import com.ktb.community.dto.member.request.MemberPatchRequest;
import com.ktb.community.dto.member.request.MemberPostRequest;
import com.ktb.community.dto.member.request.PasswordRequest;
import com.ktb.community.dto.member.response.LoginResponse;
import com.ktb.community.dto.member.response.MemberResponse;
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
    public LoginResponse signUp(final @RequestBody @Valid MemberPostRequest memberPostRequest) {
        return memberService.save(memberPostRequest);
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
    public MemberResponse getMemberProfile(@PathVariable("memberId") final long memberId) {
        return memberService.getMemberProfile(memberId);
    }

    @PatchMapping("/{memberId}/profile")
    @AccountOwner
    public MemberResponse patchMemberProfile(@PathVariable("memberId") final long memberId,
                                             @RequestBody @Valid final MemberPatchRequest memberPatchRequest) {
        return memberService.updateMemberProfile(memberId, memberPatchRequest);
    }

    @PatchMapping("/{memberId}/password")
    @AccountOwner
    public Response patchMemberPassword(@PathVariable("memberId") final long memberId,
                                    @RequestBody @Valid final PasswordRequest passwordRequest) {
        return memberService.updateMemberPassword(memberId, passwordRequest);
    }

    @PatchMapping("/{memberId}")
    @AccountOwner
    public Response softDeleteMember(@PathVariable("memberId") final long memberId) {
        return memberService.softDeleteMember(memberId);
    }

}
