package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class CrewNotFoundException extends CommonException {

    public CrewNotFoundException(final ExceptionType exceptionType, final Long crewId) {
        super(HttpStatus.NOT_FOUND, exceptionType.getStatusCode(), crewId + exceptionType.getMessage());
    }
}
