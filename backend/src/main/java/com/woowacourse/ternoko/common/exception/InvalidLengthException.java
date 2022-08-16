package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class InvalidLengthException extends BadRequestException {
    public InvalidLengthException(final ExceptionType exceptionType, final int length) {
        super(exceptionType.getStatusCode(), length + exceptionType.getMessage());
    }
}
