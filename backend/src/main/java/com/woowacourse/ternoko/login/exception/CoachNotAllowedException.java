package com.woowacourse.ternoko.login.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class CoachNotAllowedException extends BadRequestException {
    public CoachNotAllowedException(ExceptionType type) {
        super(type.getStatusCode(), type.getMessage());
    }
}
