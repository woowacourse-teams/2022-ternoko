package com.woowacourse.ternoko.common.exception.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class InterviewInvalidException extends CommonException {
    public InterviewInvalidException(final ExceptionType exceptionType) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), exceptionType.getMessage());
    }

    public InterviewInvalidException(final ExceptionType exceptionType, final Long prefix) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), prefix + exceptionType.getMessage());
    }
}
