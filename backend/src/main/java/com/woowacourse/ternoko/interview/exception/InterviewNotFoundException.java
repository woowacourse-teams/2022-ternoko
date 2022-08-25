package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class InterviewNotFoundException extends CommonException {

    public InterviewNotFoundException(final ExceptionType exceptionType, final Long interviewId) {
        super(HttpStatus.NOT_FOUND, exceptionType.getStatusCode(), interviewId + exceptionType.getMessage());
    }
}
