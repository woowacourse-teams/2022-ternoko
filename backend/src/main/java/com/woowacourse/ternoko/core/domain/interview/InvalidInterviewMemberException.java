package com.woowacourse.ternoko.core.domain.interview;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.advice.CommonException;
import org.springframework.http.HttpStatus;

public class InvalidInterviewMemberException extends CommonException {
    public InvalidInterviewMemberException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
