package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import org.springframework.http.HttpStatus;

public class InvalidStatusFindCommentException extends CommonException {

    public InvalidStatusFindCommentException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
