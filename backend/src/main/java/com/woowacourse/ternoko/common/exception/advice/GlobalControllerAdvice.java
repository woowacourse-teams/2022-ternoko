package com.woowacourse.ternoko.common.exception.advice;

import com.woowacourse.ternoko.common.exception.BadRequestException;
import com.woowacourse.ternoko.common.exception.ExceptionResponse;
import com.woowacourse.ternoko.common.exception.UnauthorizedException;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestHandler(BadRequestException e) {
        return ResponseEntity.status(e.getCode()).body(new ExceptionResponse(e.getCode().value(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedHandler(UnauthorizedException e) {
        return ResponseEntity.status(e.getCode()).body(new ExceptionResponse(e.getCode().value(), e.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(SQLException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "SQL exception이 발생했습니다."));
    }
}
