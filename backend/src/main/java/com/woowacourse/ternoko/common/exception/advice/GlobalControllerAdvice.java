package com.woowacourse.ternoko.common.exception.advice;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionResponse;
import com.woowacourse.ternoko.common.exception.UnauthorizedException;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final String LOG_FORM =
            " \n CODE : {} "
            + "\n MESSAGE : {}";
    private static final String UNHANDLE_EXCEPTION_MESSAGE = "유효하지 않은 요청입니다.";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity unhandleExceptionHandler(){
        log.error(LOG_FORM, HttpStatus.INTERNAL_SERVER_ERROR.value(), UNHANDLE_EXCEPTION_MESSAGE);
        return ResponseEntity.internalServerError().body(UNHANDLE_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestHandler(BadRequestException e) {
        log.warn(LOG_FORM, e.getCode(), e.getMessage());
        return ResponseEntity.status(e.getCode()).body(new ExceptionResponse(e.getCode().value(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedHandler(UnauthorizedException e) {
        log.warn(LOG_FORM, e.getCode(), e.getMessage());
        return ResponseEntity.status(e.getCode()).body(new ExceptionResponse(e.getCode().value(), e.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(SQLException e) {
        log.warn(LOG_FORM, HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL exception이 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "SQL exception이 발생했습니다."));
    }
}
