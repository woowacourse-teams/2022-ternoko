package com.woowacourse.ternoko.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.woowacourse.ternoko.common.AuthInterceptor;
import com.woowacourse.ternoko.common.JwtProvider;
import com.woowacourse.ternoko.service.AuthService;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

	private final JwtProvider jwtTokenProvider;
	private final AuthService authService;

	public AuthenticationPrincipalConfig(final JwtProvider jwtProvider,
		AuthService authService) {
		this.jwtTokenProvider = jwtProvider;
		this.authService = authService;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor(authService))
			.excludePathPatterns("/api/login/");
	}

	@Override
	public void addArgumentResolvers(final List argumentResolvers) {
		argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
	}

	@Bean
	public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
		return new AuthenticationPrincipalArgumentResolver(jwtTokenProvider);
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor(authService);
	}

}
