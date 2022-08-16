package com.woowacourse.ternoko.login.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class TokenNotValidException extends BadRequestException {
    public TokenNotValidException(ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
