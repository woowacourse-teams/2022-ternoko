package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.ForbiddenException;

public class InvalidInterviewCrewIdException extends ForbiddenException {

    public InvalidInterviewCrewIdException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
