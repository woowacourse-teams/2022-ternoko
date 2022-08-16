package com.woowacourse.ternoko.common.exception.advice;

import static com.woowacourse.ternoko.common.exception.ExceptionType.UNHANDLED_EXCEPTION;
import static com.woowacourse.ternoko.common.log.LogForm.FAILED_LOGGING_FORM;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.ternoko.common.exception.ExceptionResponse;
import com.woowacourse.ternoko.common.exception.form.BadRequestException;
import com.woowacourse.ternoko.common.exception.form.ForbiddenException;
import com.woowacourse.ternoko.common.exception.form.UnauthorizedException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String UNHANDLED_EXCEPTION_MESSAGE = "유효하지 않은 요청입니다.";

    private final ObjectMapper objectMapper;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestHandler(final BadRequestException e,
                                                               final HttpServletRequest request) throws IOException {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        printFailedLog(request, e, cachingRequest);
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedHandler(final UnauthorizedException e,
                                                                 final HttpServletRequest request) throws IOException {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        printFailedLog(request, e, cachingRequest);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> forbiddenHandler(final UnauthorizedException e,
                                                              final HttpServletRequest request) throws IOException {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        printFailedLog(request, e, cachingRequest);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(final Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(UNHANDLED_EXCEPTION.getStatusCode(),
                        UNHANDLED_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "SQL exception이 발생했습니다."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> unhandledExceptionHandler(final HttpServletRequest request)
            throws IOException {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        log.info(FAILED_LOGGING_FORM,
                request.getMethod(),
                request.getRequestURI(),
                StringUtils.hasText(request.getHeader("Authorization")),
                objectMapper.readTree(cachingRequest.getContentAsByteArray()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                UNHANDLED_EXCEPTION_MESSAGE);
        return ResponseEntity.internalServerError().body(new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                UNHANDLED_EXCEPTION_MESSAGE
        ));
    }

    private void printFailedLog(final HttpServletRequest request,
                                final RuntimeException e,
                                final ContentCachingRequestWrapper cachingRequest)
            throws IOException {
        log.info(FAILED_LOGGING_FORM,
                request.getMethod(),
                request.getRequestURI(),
                StringUtils.hasText(request.getHeader("Authorization")),
                objectMapper.readTree(cachingRequest.getContentAsByteArray()),
                e.getMessage());
        log.debug("Stack trace: ", e);
    }
}
