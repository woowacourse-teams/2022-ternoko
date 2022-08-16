package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class InvalidCommentInterviewIdException extends BadRequestException {

    public InvalidCommentInterviewIdException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
