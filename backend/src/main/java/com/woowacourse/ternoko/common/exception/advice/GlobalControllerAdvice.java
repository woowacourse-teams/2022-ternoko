package com.woowacourse.ternoko.common.exception.advice;

import static com.woowacourse.ternoko.common.exception.ExceptionType.UNHANDLED_EXCEPTION;
import static com.woowacourse.ternoko.common.log.LogForm.FAILED_LOGGING_FORM;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.ternoko.common.exception.CommonException;
import com.woowacourse.ternoko.common.exception.ExceptionResponse;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@RestControllerAdvice
@EnableWebMvc
@AllArgsConstructor
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponse> commonExceptionHandler(final CommonException e,
                                                                    final HttpServletRequest request) {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        printFailedLog(request, e, cachingRequest);
        return ResponseEntity
                .status(e.getHttpStatusCode())
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> unhandledExceptionHandler(final Exception e,
                                                                       final HttpServletRequest request) {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        printFailedLog(request, e, cachingRequest);
        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(UNHANDLED_EXCEPTION.getStatusCode(),
                        UNHANDLED_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(final DataAccessException e,
                                                                 final HttpServletRequest request) {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        printFailedLog(request, e, cachingRequest);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "SQL exception이 발생했습니다."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(final RuntimeException e,
                                                                     final HttpServletRequest request) {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        printFailedLog(request, e, cachingRequest);
        return ResponseEntity.internalServerError().body(new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        ));
    }

    private void printFailedLog(final HttpServletRequest request,
                                final Exception e,
                                final ContentCachingRequestWrapper cachingRequest) {
        try {
            log.info(FAILED_LOGGING_FORM,
                    request.getMethod(),
                    request.getRequestURI(),
                    StringUtils.hasText(request.getHeader("Authorization")),
                    objectMapper.readTree(cachingRequest.getContentAsByteArray()),
                    e.getMessage());
            log.debug("Stack trace: ", e);
        } catch (IOException ex) {
            log.debug("log error");
        }
    }
}
