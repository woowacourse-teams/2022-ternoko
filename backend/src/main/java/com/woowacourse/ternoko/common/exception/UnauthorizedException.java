package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CommonException {

    public UnauthorizedException(final HttpStatus code, final String message) {
        super(code, message);
    }
}
