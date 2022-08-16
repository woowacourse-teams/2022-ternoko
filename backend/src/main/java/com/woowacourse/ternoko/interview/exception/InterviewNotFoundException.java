package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class InterviewNotFoundException extends BadRequestException {

    public InterviewNotFoundException(final ExceptionType exceptionType, final Long interviewId) {
        super(exceptionType.getStatusCode(), interviewId + exceptionType.getMessage());
    }
}
