package com.woowacourse.ternoko.common.exception;

public class InvalidLengthException extends BadRequestException {

    public InvalidLengthException(final ExceptionType exceptionType, final int length) {
        super(exceptionType.getStatusCode(), length + exceptionType.getMessage());
    }
}
