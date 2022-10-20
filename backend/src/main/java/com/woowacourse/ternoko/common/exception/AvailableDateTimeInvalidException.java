package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.advice.CommonException;

public class AvailableDateTimeInvalidException extends CommonException {
    public AvailableDateTimeInvalidException(final ExceptionType exceptionType) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), exceptionType.getMessage());
    }

    public AvailableDateTimeInvalidException(final ExceptionType exceptionType, final Long prefix) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), prefix + exceptionType.getMessage());
    }
}
