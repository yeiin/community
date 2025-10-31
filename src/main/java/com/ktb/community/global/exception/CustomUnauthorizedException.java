package com.ktb.community.global.exception;

import com.ktb.community.global.constant.ExceptionConstant;
import lombok.Getter;

import static com.ktb.community.global.constant.StatusCode.UNAUTHORIZED;

@Getter
public class CustomUnauthorizedException extends RuntimeException {
    private final String statusCode = UNAUTHORIZED.getStatusCode();
    private final String message;

    public CustomUnauthorizedException(final ExceptionConstant exceptionConstant){
        this.message = exceptionConstant.message();
    }
}
