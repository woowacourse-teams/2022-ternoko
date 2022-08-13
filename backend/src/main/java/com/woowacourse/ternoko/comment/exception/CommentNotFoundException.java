package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class CommentNotFoundException extends BadRequestException {

    public CommentNotFoundException(final ExceptionType exceptionType, final Long commentId) {
        super(exceptionType.getStatusCode(), commentId + exceptionType.getMessage());
    }
}
