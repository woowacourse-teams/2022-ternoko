package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewNotFoundException extends CommonException {

    public InterviewNotFoundException(final ExceptionType exceptionType, final Long interviewId) {
        super(HttpStatus.NOT_FOUND, exceptionType.getStatusCode(), interviewId + exceptionType.getMessage());
    }
}
