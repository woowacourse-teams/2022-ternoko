package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewStatusException extends CommonException {

    public InterviewStatusException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
