package com.woowacourse.ternoko.auth.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.advice.CommonException;
import org.springframework.http.HttpStatus;

public class TokenNotValidException extends CommonException {

    public TokenNotValidException(ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
