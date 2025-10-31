package com.ktb.community.global.exception;

import com.ktb.community.global.constant.ExceptionConstant;
import lombok.Getter;

import static com.ktb.community.global.constant.StatusCode.CONFLICT;

@Getter
public class CustomConflictException extends RuntimeException {
    private final String statusCode = CONFLICT.getStatusCode();
    private final String message;

    public CustomConflictException(final ExceptionConstant exceptionConstant){
        this.message = exceptionConstant.message();
    }
}
