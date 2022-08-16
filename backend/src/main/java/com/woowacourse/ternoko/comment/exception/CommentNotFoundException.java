package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class CommentNotFoundException extends BadRequestException {

    public CommentNotFoundException(final ExceptionType exceptionType, final Long commentId) {
        super(exceptionType.getStatusCode(), commentId + exceptionType.getMessage());
    }
}
