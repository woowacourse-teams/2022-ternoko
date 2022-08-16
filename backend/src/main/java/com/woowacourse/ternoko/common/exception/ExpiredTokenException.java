package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.form.UnauthorizedException;

public class ExpiredTokenException extends UnauthorizedException {
    public ExpiredTokenException(ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
