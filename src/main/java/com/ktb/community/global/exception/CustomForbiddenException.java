package com.ktb.community.global.exception;

import com.ktb.community.global.constant.ExceptionConstant;
import lombok.Getter;

import static com.ktb.community.global.constant.StatusCode.FORBIDDEN;

@Getter
public class CustomForbiddenException extends RuntimeException {
    private final String statusCode = FORBIDDEN.getStatusCode();
    private final String message;

    public CustomForbiddenException(final ExceptionConstant exceptionConstant){
        this.message = exceptionConstant.message();
    }
}
