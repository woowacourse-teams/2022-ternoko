package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidInterviewCoachIdException extends CommonException {

    public InvalidInterviewCoachIdException(final ExceptionType exceptionType) {
        super(HttpStatus.FORBIDDEN, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
