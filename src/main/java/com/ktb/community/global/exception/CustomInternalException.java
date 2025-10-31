package com.ktb.community.global.exception;

import com.ktb.community.global.constant.ExceptionConstant;
import lombok.Getter;

import static com.ktb.community.global.constant.StatusCode.INTERNAL_SERVER_ERROR;

@Getter
public class CustomInternalException extends RuntimeException {
    private final String statusCode = INTERNAL_SERVER_ERROR.getStatusCode();
    private final String message;

    public CustomInternalException(final ExceptionConstant exceptionConstant){
        this.message = exceptionConstant.message();
    }
}
