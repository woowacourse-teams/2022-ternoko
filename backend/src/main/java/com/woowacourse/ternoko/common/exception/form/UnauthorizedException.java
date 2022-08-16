package com.woowacourse.ternoko.common.exception.form;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final int code;

    public UnauthorizedException(final int code, final String message) {
        super(message);
        this.code = code;
    }
}
