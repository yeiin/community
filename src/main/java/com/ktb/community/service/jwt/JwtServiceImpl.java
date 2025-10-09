package com.ktb.community.service.jwt;

import com.ktb.community.dto.member.response.LoginResponse;
import com.ktb.community.global.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.jwt.access_exp_time}")
    private long accessExpTime;

    @Value("${spring.jwt.refresh_exp_time}")
    private long refreshExpTime;

    private JwtUtil jwtUtil;

    public JwtServiceImpl(final JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @Override
    public LoginResponse createJwts(final long memberId) {
        String accessToken = jwtUtil.createToken(memberId, accessExpTime);
        String refreshToken = jwtUtil.createToken(memberId, refreshExpTime);
        return new LoginResponse(memberId, accessToken, refreshToken);
    }


}
