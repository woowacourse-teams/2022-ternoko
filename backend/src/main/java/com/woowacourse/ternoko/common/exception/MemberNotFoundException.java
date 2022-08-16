package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class MemberNotFoundException extends BadRequestException {

    public MemberNotFoundException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
