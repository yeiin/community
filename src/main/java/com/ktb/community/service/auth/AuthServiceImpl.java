package com.ktb.community.service.auth;

import com.ktb.community.domain.auth.Auth;
import com.ktb.community.domain.member.Member;
import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.auth.response.LoginResponse;
import com.ktb.community.global.encrypt.EncryptEncoder;
import com.ktb.community.global.exception.CustomBadRequestException;
import com.ktb.community.global.exception.CustomForbiddenException;
import com.ktb.community.global.exception.CustomNotFoundException;
import com.ktb.community.global.provider.JwtProvider;
import com.ktb.community.repository.auth.AuthRepository;
import com.ktb.community.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.ktb.community.global.constant.CustomConstant.LOGOUT_SUCCESS;
import static com.ktb.community.global.constant.ExceptionConstant.*;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @PersistenceContext
    private EntityManager em;

    private final AuthRepository authRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final EncryptEncoder encryptEncoder;

    public AuthServiceImpl(final AuthRepository authRepository, final MemberRepository memberRepository,
                           final JwtProvider jwtProvider, final EncryptEncoder encryptEncoder, final EntityManager em) {
        this.authRepository = authRepository;
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
        this.encryptEncoder = encryptEncoder;
        this.em = em;
    }

    @Transactional
    @Override
    public LoginResponse createJwts(final long memberId) {
        LoginResponse loginResponse = jwtProvider.createTokens(memberId);
        saveAuth(memberId, loginResponse.refreshToken());

        return loginResponse;
    }

    private void saveAuth(long memberId, String refreshToken) {
        Optional<Auth> auth = authRepository.findByMemberId(memberId);

        if(auth.isPresent()) {
            auth.get().updateRefreshTokenHash(encryptEncoder.sha256Encrypt(refreshToken));
        }else {
            Member member = memberRepository.getById(memberId);
            authRepository.save(new Auth(member, encryptEncoder.sha256Encrypt(refreshToken)));
        }
    }


    @Transactional
    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(()-> new CustomNotFoundException(MEMBER_NOT_FOUND));

        if(!encryptEncoder.bcryptIsMatch(loginRequest.password(), member.getPassword())) {
            throw new CustomBadRequestException(PASSWORD_BAD_REQUEST);
        }

        return createJwts(member.getId());
    }

    @Transactional
    @Override
    public LoginResponse updateWithRefreshToken(final String refreshToken) {
        jwtProvider.validateToken(refreshToken);

        Auth auth = authRepository.findByRefreshTokenHash(encryptEncoder.sha256Encrypt(refreshToken))
                .orElseThrow(()-> new CustomNotFoundException(AUTH_NOT_FOUND));

        return createJwts(auth.getMember().getId());
    }

    @Transactional
    @Override
    public Response logout(final long memberId) {
        authRepository.deleteByMemberId(memberId);
        return Response.of(HttpStatus.OK, LOGOUT_SUCCESS.message());
    }

    @Transactional
    @Override
    public void deleteAuthByMemberId(final long memberId) {
        authRepository.deleteByMemberId(memberId);
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
