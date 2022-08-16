package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class InterviewStatusException extends BadRequestException {
    public InterviewStatusException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
