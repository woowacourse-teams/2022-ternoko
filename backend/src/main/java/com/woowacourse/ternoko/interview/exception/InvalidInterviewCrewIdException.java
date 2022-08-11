package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class InvalidInterviewCrewIdException extends BadRequestException {

    public InvalidInterviewCrewIdException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
