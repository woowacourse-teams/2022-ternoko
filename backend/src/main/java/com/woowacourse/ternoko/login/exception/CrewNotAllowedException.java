package com.woowacourse.ternoko.login.exception;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionType;

public class CrewNotAllowedException extends BadRequestException {
    public CrewNotAllowedException(ExceptionType type) {
        super(type.getStatusCode(), type.getMessage());
    }
}
