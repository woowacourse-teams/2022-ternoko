package com.woowacourse.ternoko.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestHandler(BadRequestException e) {
        return ResponseEntity.status(e.getCode()).body(new ExceptionResponse(e.getCode().value(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedHandler(UnauthorizedException e) {
        return ResponseEntity.status(e.getCode()).body(new ExceptionResponse(e.getCode().value(), e.getMessage()));
    }
}
