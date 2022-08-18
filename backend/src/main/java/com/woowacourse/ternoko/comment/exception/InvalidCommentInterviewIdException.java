package com.woowacourse.ternoko.comment.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import org.springframework.http.HttpStatus;

public class InvalidCommentInterviewIdException extends CommonException {

    public InvalidCommentInterviewIdException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
