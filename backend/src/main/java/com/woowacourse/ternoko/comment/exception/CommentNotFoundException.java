package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CommonException {

    public CommentNotFoundException(final ExceptionType exceptionType, final Long commentId) {
        super(HttpStatus.NOT_FOUND, exceptionType.getStatusCode(), commentId + exceptionType.getMessage());
    }
}
