package com.woowacourse.ternoko.common.log;

import static com.woowacourse.ternoko.common.log.LogForm.SUCCESS_LOGGING_FORM;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (isSuccess(response.getStatus())) {
            final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;

            log.info(SUCCESS_LOGGING_FORM, request.getMethod(),
                    request.getRequestURI(),
                    StringUtils.hasText(request.getHeader("Authorization")),
                    objectMapper.readTree(cachingRequest.getContentAsByteArray()));
        }
    }

    private boolean isSuccess(int responseStatus) {
        return !HttpStatus.valueOf(responseStatus).is4xxClientError() && !HttpStatus.valueOf(responseStatus)
                .is5xxServerError();
    }
}
