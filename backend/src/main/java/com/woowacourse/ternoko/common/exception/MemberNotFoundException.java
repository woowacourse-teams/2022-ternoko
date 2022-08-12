package com.woowacourse.ternoko.common.exception;

public class MemberNotFoundException extends BadRequestException {

    public MemberNotFoundException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
