package com.woowacourse.ternoko.common.exception;

public class InvalidReservationCoachIdException extends BadRequestException {

    public InvalidReservationCoachIdException(final ExceptionType exceptionType) {
        super(exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
