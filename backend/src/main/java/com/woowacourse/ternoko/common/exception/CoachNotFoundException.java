package com.woowacourse.ternoko.common.exception;

import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class CoachNotFoundException extends CommonException {

    public CoachNotFoundException(final ExceptionType exceptionType, final Long coachId) {
        super(HttpStatus.NOT_FOUND, exceptionType.getStatusCode(), coachId + exceptionType.getMessage());
    }
}
