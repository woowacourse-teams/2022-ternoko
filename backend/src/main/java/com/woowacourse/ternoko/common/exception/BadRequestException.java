package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CommonException {

    public BadRequestException(final HttpStatus code, final String message) {
        super(code, message);
    }
}
