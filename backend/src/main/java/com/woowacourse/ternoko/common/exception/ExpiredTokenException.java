package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends CommonException {

    public ExpiredTokenException(ExceptionType exceptionType) {
        super(HttpStatus.FORBIDDEN, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
