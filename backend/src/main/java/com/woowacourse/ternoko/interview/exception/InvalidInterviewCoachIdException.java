package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class InvalidInterviewCoachIdException extends BadRequestException {

    public InvalidInterviewCoachIdException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
