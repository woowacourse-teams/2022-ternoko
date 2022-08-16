package com.woowacourse.ternoko.login.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class CoachNotAllowedException extends BadRequestException {
    public CoachNotAllowedException(ExceptionType type) {
        super(type.getStatusCode(), type.getMessage());
    }
}
