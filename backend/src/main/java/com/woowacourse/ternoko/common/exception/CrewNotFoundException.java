package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class CrewNotFoundException extends CommonException {

    public CrewNotFoundException(final ExceptionType exceptionType, final Long crewId) {
        super(HttpStatus.NOT_FOUND, exceptionType.getStatusCode(), crewId + exceptionType.getMessage());
    }
}
