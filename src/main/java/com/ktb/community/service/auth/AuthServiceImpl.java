package com.ktb.community.service.auth;

import com.ktb.community.domain.auth.Auth;
import com.ktb.community.domain.member.Member;
import com.ktb.community.dto.Response;
import com.ktb.community.dto.auth.request.LoginRequest;
import com.ktb.community.dto.auth.request.RefreshTokenRequest;
import com.ktb.community.dto.member.response.LoginResponse;
import com.ktb.community.global.encrypt.EncryptEncoder;
import com.ktb.community.global.jwt.JwtUtil;
import com.ktb.community.repository.auth.AuthRepository;
import com.ktb.community.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Value("${spring.jwt.access_exp_time}")
    private long accessExpTime;

    @Value("${spring.jwt.refresh_exp_time}")
    private long refreshExpTime;

    @PersistenceContext
    private EntityManager em;

    private final AuthRepository authRepository;
    private final MemberRepository memberRepository;
    private JwtUtil jwtUtil;
    private final EncryptEncoder encryptEncoder;

    public AuthServiceImpl(final AuthRepository authRepository, final MemberRepository memberRepository,
                           final JwtUtil jwtUtil, final EncryptEncoder encryptEncoder, final EntityManager em) {
        this.authRepository = authRepository;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.encryptEncoder = encryptEncoder;
        this.em = em;
    }

    @Transactional
    @Override
    public LoginResponse createJwts(final long memberId) {
        String accessToken = jwtUtil.createToken(memberId, accessExpTime);
        String refreshToken = jwtUtil.createToken(memberId, refreshExpTime);

        saveAuth(memberId, refreshToken);

        return new LoginResponse(memberId, accessToken, refreshToken);
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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if(!encryptEncoder.bcryptIsMatch(loginRequest.password(), member.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
        }

        return createJwts(member.getId());
    }

    @Transactional
    @Override
    public LoginResponse updateWithRefreshToken(final RefreshTokenRequest refreshTokenRequest) {
        jwtUtil.validateToken(refreshTokenRequest.refreshToken());

        Auth auth = authRepository.findByRefreshTokenHash(encryptEncoder.sha256Encrypt(refreshTokenRequest.refreshToken()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "리프레쉬 토큰 정보를 찾을 수 없습니다."));

        return createJwts(auth.getMember().getId());
    }

    @Transactional
    @Override
    public Response logout(final long memberId) {
        authRepository.deleteByMemberId(memberId);
        return Response.of(HttpStatus.OK, "로그아웃에 성공했습니다.");
    }

    @Transactional
    @Override
    public void deleteAuthByMemberId(final long memberId) {
        authRepository.deleteByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkAccountOwner(final long memberId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long authId = Long.parseLong(authentication.getPrincipal().toString());

        if(authId != memberId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 사용자의 계정에는 접근할 수 없습니다.");
        }

        return true;
    }
}
