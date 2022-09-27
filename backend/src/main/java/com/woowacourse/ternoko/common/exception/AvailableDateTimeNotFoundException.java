package com.woowacourse.ternoko.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AvailableDateTimeNotFoundException extends CommonException {
    public AvailableDateTimeNotFoundException(final ExceptionType exceptionType, final Long id) {
        super(NOT_FOUND, exceptionType.getStatusCode(), exceptionType.getMessage() + id);
    }
}
