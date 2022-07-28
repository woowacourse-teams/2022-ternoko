package com.woowacourse.ternoko.common.exception;

public class InvalidReservationCrewIdException extends BadRequestException {

    public InvalidReservationCrewIdException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
