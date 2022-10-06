package com.woowacourse.ternoko.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidMemberNicknameException extends CommonException {

    public InvalidMemberNicknameException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
