package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.advice.CommonException;

public class InterviewInvalidException extends CommonException {
    public InterviewInvalidException(final ExceptionType exceptionType) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), exceptionType.getMessage());
    }

    public InterviewInvalidException(final ExceptionType exceptionType, final Long prefix) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), prefix + exceptionType.getMessage());
    }
}
