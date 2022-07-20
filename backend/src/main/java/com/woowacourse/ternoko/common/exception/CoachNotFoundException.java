package com.woowacourse.ternoko.common.exception;

public class CoachNotFoundException extends BadRequestException {

    public CoachNotFoundException(final ExceptionType exceptionType, final Long coachId) {
        super(exceptionType.getCode(), coachId + exceptionType.getMessage());
    }
}
