package com.ktb.community.dto;

import org.springframework.http.HttpStatus;

public record Response(
        int statusCode,
        String statusMessage,
        String message
) {

    public static Response of(final HttpStatus status, final String message) {
        return new Response(status.value(), status.getReasonPhrase(), message);
    }
}
