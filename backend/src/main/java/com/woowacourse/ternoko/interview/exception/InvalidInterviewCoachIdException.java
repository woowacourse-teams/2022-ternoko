package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.ForbiddenException;

public class InvalidInterviewCoachIdException extends ForbiddenException {
    public InvalidInterviewCoachIdException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
