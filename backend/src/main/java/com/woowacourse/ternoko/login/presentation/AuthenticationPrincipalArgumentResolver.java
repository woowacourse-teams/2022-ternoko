package com.woowacourse.ternoko.login.presentation;

import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_TOKEN;

import com.woowacourse.ternoko.login.aop.MemberTypeCache;
import com.woowacourse.ternoko.login.application.JwtProvider;
import com.woowacourse.ternoko.login.domain.AuthenticationPrincipal;
import com.woowacourse.ternoko.login.exception.TokenNotValidException;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@AllArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final MemberTypeCache memberTypeCache;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {
        final String credentials = AuthorizationExtractor.extract(
                webRequest.getNativeRequest(HttpServletRequest.class));

        try {
            memberTypeCache.setMemberType(jwtProvider.getMemberType(credentials));
        } catch (NumberFormatException e) {
            throw new TokenNotValidException(INVALID_TOKEN);
        }

        jwtProvider.validateToken(credentials);
        return Long.valueOf(jwtProvider.extractSubject(credentials));
    }
}
