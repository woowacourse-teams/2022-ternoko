package com.woowacourse.ternoko.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidAvailableDateTimeException extends CommonException {
    public InvalidAvailableDateTimeException(final ExceptionType exceptionType) {
        super(BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
