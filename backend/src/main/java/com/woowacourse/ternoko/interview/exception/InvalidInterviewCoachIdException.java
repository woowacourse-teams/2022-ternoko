package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import org.springframework.http.HttpStatus;

public class InvalidInterviewCoachIdException extends CommonException {

    public InvalidInterviewCoachIdException(final ExceptionType exceptionType) {
        super(HttpStatus.FORBIDDEN, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
