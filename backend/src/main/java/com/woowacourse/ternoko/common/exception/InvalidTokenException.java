package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.advice.CommonException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CommonException {

    public InvalidTokenException(final ExceptionType exceptionType) {
        super(HttpStatus.UNAUTHORIZED, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
