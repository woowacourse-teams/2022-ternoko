package com.woowacourse.ternoko.common.exception;

public class ReservationNotFoundException extends BadRequestException {

    public ReservationNotFoundException(final ExceptionType exceptionType, final Long reservationId) {
        super(exceptionType.getStatusCode(), reservationId + exceptionType.getMessage());
    }
}
