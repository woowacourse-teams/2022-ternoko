package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class InvalidStatusFindCommentException extends BadRequestException {

    public InvalidStatusFindCommentException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
