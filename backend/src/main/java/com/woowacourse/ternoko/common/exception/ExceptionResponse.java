package com.woowacourse.ternoko.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {
    private int code;
    private String message;

    public static ExceptionResponse from(final CommonException e){
        return new ExceptionResponse(e.getCode().value(), e.getMessage());
    }
}
