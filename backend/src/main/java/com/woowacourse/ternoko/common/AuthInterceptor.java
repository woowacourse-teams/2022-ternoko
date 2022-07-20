package com.woowacourse.ternoko.common;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.InvalidTokenException;
import com.woowacourse.ternoko.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private AuthService authService;

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
