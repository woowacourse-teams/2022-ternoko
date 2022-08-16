package com.woowacourse.ternoko.login.exception;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;

public class CrewNotAllowedException extends BadRequestException {
    public CrewNotAllowedException(ExceptionType type) {
        super(type.getStatusCode(), type.getMessage());
    }
}
