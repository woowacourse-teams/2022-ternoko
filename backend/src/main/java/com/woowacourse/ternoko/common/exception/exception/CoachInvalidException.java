package com.woowacourse.ternoko.common.exception.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class CoachInvalidException extends CommonException {

    public CoachInvalidException(final ExceptionType exceptionType, final Long coachId) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), coachId + exceptionType.getMessage());
    }
}

