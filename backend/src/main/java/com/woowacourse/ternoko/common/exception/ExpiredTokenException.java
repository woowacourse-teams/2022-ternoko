package com.woowacourse.ternoko.common.exception;

public class ExpiredTokenException extends UnauthorizedException {

    public ExpiredTokenException(ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
