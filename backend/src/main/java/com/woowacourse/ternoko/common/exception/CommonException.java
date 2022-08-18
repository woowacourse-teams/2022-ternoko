package com.woowacourse.ternoko.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonException extends RuntimeException {

    private final HttpStatus httpStatusCode;
    private final int code;
    private final String message;
}
