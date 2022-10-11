package com.woowacourse.ternoko.common.exception.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class CrewInvalidException extends CommonException {
    public CrewInvalidException(final ExceptionType exceptionType, final Long crewId) {
        super(exceptionType.getHttpStatus(), exceptionType.getStatusCode(), crewId + exceptionType.getMessage());
    }
}
