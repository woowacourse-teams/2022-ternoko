package com.woowacourse.ternoko.auth.presentation;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_TOKEN;

import com.woowacourse.ternoko.auth.application.AuthorizationExtractor;
import com.woowacourse.ternoko.auth.application.JwtProvider;
import com.woowacourse.ternoko.auth.presentation.annotation.AuthenticationPrincipal;
import com.woowacourse.ternoko.auth.exception.TokenNotValidException;
import com.woowacourse.ternoko.auth.presentation.MemberTypeCache;
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
