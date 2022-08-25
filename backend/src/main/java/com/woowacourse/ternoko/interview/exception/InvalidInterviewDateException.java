package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class InvalidInterviewDateException extends CommonException {

    public InvalidInterviewDateException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
