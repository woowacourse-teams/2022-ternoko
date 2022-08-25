package com.woowacourse.ternoko.login.application;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import com.woowacourse.ternoko.common.exception.InvalidTokenException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            throw new InvalidTokenException(ExceptionType.UNAUTHORIZED_MEMBER);
        }
        return authService.isValid(header);
    }
}
