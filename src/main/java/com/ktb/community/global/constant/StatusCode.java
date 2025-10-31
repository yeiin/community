package com.ktb.community.global.constant;

public enum StatusCode {

    OK("200"),
    CREATED("201"),
    MOVED_PERMANENTLY("301"),
    TEMPORARY_REDIRECT("307"),
    BAD_REQUEST("400"),
    UNAUTHORIZED("401"),
    FORBIDDEN("403"),
    NOT_FOUND("404"),
    CONFLICT("409"),
    INTERNAL_SERVER_ERROR("500");

    private String statusCode;

    StatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    public String getStatusCode() {
        return statusCode;
    }
}
