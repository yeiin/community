package com.ktb.community.global.exception;

import com.ktb.community.dto.Response;
import com.ktb.community.global.annotation.Login;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    public CustomExceptionHandler(){

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBadRequestException.class)
    public Response handleException(final CustomBadRequestException e,
                                    final HttpServletRequest httpServletRequest,
                                    @Login final long loginId) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n", loginId, httpServletRequest.getRequestURI(), e.getMessage());

        return new Response(e.getStatusCode(), e.getMessage());
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    public Response handleException(final CustomUnauthorizedException e,
                                    final HttpServletRequest httpServletRequest,
                                    @Login final long loginId) {
        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n", loginId, httpServletRequest.getRequestURI(), e.getMessage());

        return new Response(e.getStatusCode(), e.getMessage());
    }

    // 403
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(CustomForbiddenException.class)
    public Response handleException(final CustomForbiddenException e,
                                    final HttpServletRequest httpServletRequest,
                                    @Login final long loginId) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n", loginId, httpServletRequest.getRequestURI(), e.getMessage());

        return new Response(e.getStatusCode(), e.getMessage());
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public Response handleException(final CustomNotFoundException e,
                                    final HttpServletRequest httpServletRequest,
                                    @Login final long loginId) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n", loginId, httpServletRequest.getRequestURI(), e.getMessage());

        return new Response(e.getStatusCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomConflictException.class)
    public Response handleException(final CustomConflictException e,
                                    final HttpServletRequest httpServletRequest,
                                    @Login final long loginId) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n", loginId, httpServletRequest.getRequestURI(), e.getMessage());

        return new Response(e.getStatusCode(), e.getMessage());
    }

    // 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomInternalException.class)
    public Response handleInternalServerError(final CustomInternalException e,
                                              final HttpServletRequest httpServletRequest,
                                              @Login final long loginId) {
        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n", loginId, httpServletRequest.getRequestURI(), e.getMessage());

        return new Response(e.getStatusCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public Response handleMultipartException(MaxUploadSizeExceededException e) {
        log.info("handleMaxUploadSizeExceededException", e);

        return new Response(e.getStatusCode().toString(), e.getMessage());
    }

}
