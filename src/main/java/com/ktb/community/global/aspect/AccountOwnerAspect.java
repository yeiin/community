package com.ktb.community.global.aspect;

import com.ktb.community.global.annotation.AccountOwner;
import com.ktb.community.global.exception.CustomForbiddenException;
import com.ktb.community.global.exception.CustomUnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.ktb.community.global.constant.ExceptionConstant.ACCOUNT_FORBIDDEN;
import static com.ktb.community.global.constant.ExceptionConstant.MEMBER_UNAUTHORIZED;

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
            throw new CustomUnauthorizedException(MEMBER_UNAUTHORIZED);
        }

        if(memberId != Long.parseLong(accountOwner.memberId())){
            throw new CustomForbiddenException(ACCOUNT_FORBIDDEN);
        }

    }
}
