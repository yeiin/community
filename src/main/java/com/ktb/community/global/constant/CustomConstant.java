package com.ktb.community.global.constant;

public enum CustomConstant {
    LOGOUT_SUCCESS("로그아웃에 성공했습니다.");

    private String message;
    CustomConstant(String message) {
        this.message = message;
    }
    public String message() {
        return message;
    }
}
