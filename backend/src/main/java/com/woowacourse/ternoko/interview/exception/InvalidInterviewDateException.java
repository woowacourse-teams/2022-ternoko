package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class InvalidInterviewDateException extends BadRequestException {

    public InvalidInterviewDateException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
