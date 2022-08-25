package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CommonException {

    public MemberNotFoundException(final ExceptionType exceptionType) {
        super(HttpStatus.NOT_FOUND, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
