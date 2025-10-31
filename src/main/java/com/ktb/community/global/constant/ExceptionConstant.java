package com.ktb.community.global.constant;

public enum ExceptionConstant{

    MEMBER_UNAUTHORIZED("로그인이 필요합니다."),
    INVALID_TOKEN("잘못된 양식의 토큰입니다."),

    ACCOUNT_FORBIDDEN("다른 사용자의 계정에는 접근할 수 없습니다."),

    MEMBER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    AUTH_NOT_FOUND("리프레쉬 토큰 정보를 찾을 수 없습니다."),
    POST_NOT_FOUND("게시물을 찾을 수 없습니다."),
    POST_LIKE_NOT_FOUND("게시물의 좋아요 정보를 찾을 수 없습니다."),
    POST_STATS_NOT_FOUND("게시물 집계 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),

    NICKNAME_CONFLICT("이미 존재하는 닉네임입니다."),
    EMAIL_CONFLICT("이미 존재하는 이메일입니다."),
    POST_LIKE_CONFLICT("이미 좋아요한 게시물입니다."),

    PASSWORD_BAD_REQUEST("잘못된 비밀번호입니다.");

    private final String message;

    ExceptionConstant(String message){
        this.message = message;
    }

    public String message(){
        return message;
    }
}
