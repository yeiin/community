package com.ktb.community.global.exception;

import com.ktb.community.global.constant.ExceptionConstant;
import lombok.Getter;

import static com.ktb.community.global.constant.StatusCode.NOT_FOUND;

@Getter
public class CustomNotFoundException extends RuntimeException {
    private final String statusCode = NOT_FOUND.getStatusCode();
    private final String message;

    public CustomNotFoundException(final ExceptionConstant exceptionConstant){
        this.message = exceptionConstant.message();
    }
}
