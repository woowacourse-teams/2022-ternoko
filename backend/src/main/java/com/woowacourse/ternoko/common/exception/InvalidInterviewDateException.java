package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidInterviewDateException extends CommonException {

    public InvalidInterviewDateException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
