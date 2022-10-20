package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.advice.CommonException;

public class CoachInvalidException extends CommonException {

    public CoachInvalidException(final ExceptionType exceptionType, final Long coachId) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), coachId + exceptionType.getMessage());
    }
}

