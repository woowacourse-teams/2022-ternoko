package com.woowacourse.ternoko.common.exception.form;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final int code;

    public ForbiddenException(final int code, final String message) {
        super(message);
        this.code = code;
    }
}
