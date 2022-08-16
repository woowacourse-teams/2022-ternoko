package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class CrewNotFoundException extends BadRequestException {
    public CrewNotFoundException(final ExceptionType exceptionType, final Long crewId) {
        super(exceptionType.getStatusCode(), crewId + exceptionType.getMessage());
    }
}
