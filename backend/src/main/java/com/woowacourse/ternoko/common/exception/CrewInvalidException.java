package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.advice.CommonException;

public class CrewInvalidException extends CommonException {
    public CrewInvalidException(final ExceptionType exceptionType, final Long crewId) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), crewId + exceptionType.getMessage());
    }
}
