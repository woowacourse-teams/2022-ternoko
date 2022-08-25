package com.woowacourse.ternoko.login.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public class CrewNotAllowedException extends CommonException {
    public CrewNotAllowedException(ExceptionType type) {
        super(HttpStatus.FORBIDDEN, type.getStatusCode(), type.getMessage());
    }
}
