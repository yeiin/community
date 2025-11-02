package com.ktb.community.service.auth;

import com.ktb.community.domain.member.Member;
import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.member.response.MemberResponse;
import com.ktb.community.global.encrypt.EncryptEncoder;
import com.ktb.community.global.exception.CustomBadRequestException;
import com.ktb.community.global.exception.CustomForbiddenException;
import com.ktb.community.global.exception.CustomNotFoundException;
import com.ktb.community.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.ktb.community.global.constant.CustomConstant.LOGOUT_SUCCESS;
import static com.ktb.community.global.constant.ExceptionConstant.*;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @PersistenceContext
    private EntityManager em;

    private final MemberRepository memberRepository;
    private final EncryptEncoder encryptEncoder;

    public AuthServiceImpl(final MemberRepository memberRepository,
                           final EncryptEncoder encryptEncoder, final EntityManager em) {
        this.memberRepository = memberRepository;
        this.encryptEncoder = encryptEncoder;
        this.em = em;
    }

    @Transactional
    @Override
    public MemberResponse login(final LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(()-> new CustomNotFoundException(MEMBER_NOT_FOUND));

        if(!encryptEncoder.bcryptIsMatch(loginRequest.password(), member.getPassword())) {
            throw new CustomBadRequestException(PASSWORD_BAD_REQUEST);
        }

        return MemberResponse.from(member);
    }

    @Transactional
    @Override
    public Response logout(final long memberId) {
        return Response.of(HttpStatus.OK, LOGOUT_SUCCESS.message());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkAccountOwner(final long memberId) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        long authId = Long.parseLong(request.getAttribute("memberId").toString());

        if(authId != memberId) {
            throw new CustomForbiddenException(ACCOUNT_FORBIDDEN);
        }

        return true;
    }
}
