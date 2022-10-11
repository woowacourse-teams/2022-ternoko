package com.woowacourse.ternoko.common.exception.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class CommentInvalidException extends CommonException {
    public CommentInvalidException(final ExceptionType exceptionType) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), exceptionType.getMessage());
    }

    public CommentInvalidException(final ExceptionType exceptionType, final Long prefix) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), prefix + exceptionType.getMessage());
    }
}
