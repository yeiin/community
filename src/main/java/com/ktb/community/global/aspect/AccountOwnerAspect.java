package com.ktb.community.global.aspect;

import com.ktb.community.global.annotation.AccountOwner;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class AccountOwnerAspect {

    private final HttpServletRequest request;

    public AccountOwnerAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("@annotation(accountOwner)")
    public void beforeAccountOwner(AccountOwner accountOwner) {
        Long memberId = (Long) request.getAttribute("memberId");

        if (memberId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if(memberId != Long.parseLong(accountOwner.memberId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 사용자의 계정에는 접근할 수 없습니다.");
        }

    }
}
