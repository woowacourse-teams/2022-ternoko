package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class InvalidLengthException extends CommonException {

    public InvalidLengthException(final ExceptionType exceptionType, final int length) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), length + exceptionType.getMessage());
    }
}
