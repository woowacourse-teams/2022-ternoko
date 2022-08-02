package com.woowacourse.ternoko.common.exception;

public class InterviewStatusException extends BadRequestException {
    public InterviewStatusException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
