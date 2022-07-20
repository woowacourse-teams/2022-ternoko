package com.woowacourse.ternoko.common.exception;

public class InvalidReservationDateException extends BadRequestException {

	public InvalidReservationDateException(final ExceptionType exceptionType) {
		super(exceptionType.getCode(), exceptionType.getMessage());
	}
}
