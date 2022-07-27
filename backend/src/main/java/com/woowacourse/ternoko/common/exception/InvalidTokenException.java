package com.woowacourse.ternoko.common.exception;

public class InvalidTokenException extends UnauthorizedException {

    public InvalidTokenException(ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
