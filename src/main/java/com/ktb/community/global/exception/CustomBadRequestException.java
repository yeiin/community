package com.ktb.community.global.exception;

import com.ktb.community.global.constant.ExceptionConstant;
import lombok.Getter;

import static com.ktb.community.global.constant.StatusCode.BAD_REQUEST;

@Getter
public class CustomBadRequestException extends RuntimeException {
    private final String statusCode = BAD_REQUEST.getStatusCode();
    private final String message;

    public CustomBadRequestException(final ExceptionConstant exceptionConstant){
        this.message = exceptionConstant.message();
    }
}
