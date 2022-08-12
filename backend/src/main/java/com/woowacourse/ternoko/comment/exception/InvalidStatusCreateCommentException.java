package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class InvalidStatusCreateCommentException extends BadRequestException {

    public InvalidStatusCreateCommentException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
