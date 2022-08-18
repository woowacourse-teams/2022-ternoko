package com.woowacourse.ternoko.login.exception;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import org.springframework.http.HttpStatus;

public class CoachNotAllowedException extends CommonException {

    public CoachNotAllowedException(ExceptionType type) {
        super(HttpStatus.FORBIDDEN, type.getStatusCode(), type.getMessage());
    }
}
