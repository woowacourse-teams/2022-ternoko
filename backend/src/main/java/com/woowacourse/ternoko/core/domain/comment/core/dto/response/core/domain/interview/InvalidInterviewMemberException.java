package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview;

import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import org.springframework.http.HttpStatus;

public class InvalidInterviewMemberException extends CommonException {
    public InvalidInterviewMemberException(final ExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType.getStatusCode(), exceptionType.getMessage());
    }
}
