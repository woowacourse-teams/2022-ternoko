package com.woowacourse.ternoko.interview.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class InterviewNotFoundException extends BadRequestException {

    public InterviewNotFoundException(final ExceptionType exceptionType, final Long reservationId) {
        super(exceptionType.getStatusCode(), reservationId + exceptionType.getMessage());
    }
}
