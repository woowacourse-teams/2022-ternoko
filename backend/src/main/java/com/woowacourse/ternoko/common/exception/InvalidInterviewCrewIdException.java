package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidInterviewCrewIdException extends CommonException {

    public InvalidInterviewCrewIdException(final ExceptionType exceptionType) {
        super(HttpStatus.FORBIDDEN, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
