package com.woowacourse.ternoko.auth.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.advice.CommonException;
import org.springframework.http.HttpStatus;

public class CrewNotAllowedException extends CommonException {

    public CrewNotAllowedException(ExceptionType type) {
        super(HttpStatus.FORBIDDEN, type.getStatusCode(), type.getMessage());
    }
}
