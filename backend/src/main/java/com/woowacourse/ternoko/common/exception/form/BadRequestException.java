package com.woowacourse.ternoko.common.exception.form;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final int code;

    public BadRequestException(final int code, final String message) {
        super(message);
        this.code = code;
    }
}
