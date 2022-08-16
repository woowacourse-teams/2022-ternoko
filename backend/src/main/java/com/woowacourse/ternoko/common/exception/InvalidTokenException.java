package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.form.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
    public InvalidTokenException(ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
