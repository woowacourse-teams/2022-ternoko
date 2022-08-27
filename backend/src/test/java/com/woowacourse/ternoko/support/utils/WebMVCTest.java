package com.woowacourse.ternoko.support.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.ternoko.api.ControllerSupporter;
import com.woowacourse.ternoko.common.log.LogInterceptor;
import com.woowacourse.ternoko.config.WebConfig;
import com.woowacourse.ternoko.login.aop.MemberTypeCache;
import com.woowacourse.ternoko.login.application.AuthInterceptor;
import com.woowacourse.ternoko.login.application.AuthService;
import com.woowacourse.ternoko.login.application.JwtProvider;
import com.woowacourse.ternoko.login.presentation.AuthenticationPrincipalConfig;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@WebMvcTest(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = RestController.class))
@Import({JwtProvider.class, WebConfig.class, AuthenticationPrincipalConfig.class,
        MemberTypeCache.class, AuthInterceptor.class, LogInterceptor.class})
public abstract class WebMVCTest extends ControllerSupporter {

    @Autowired
    protected AuthenticationPrincipalConfig authenticationPrincipalConfig;

    @Autowired
    protected WebConfig webConfig;

    @Autowired
    protected AuthInterceptor authInterceptor;

    @MockBean
    protected LogInterceptor logInterceptor;

    @MockBean
    protected MemberTypeCache memberTypeCache;

    @MockBean
    protected AuthService authService;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected HttpServletRequest httpServletRequest;

    @MockBean
    protected NativeWebRequest nativeWebRequest;

}
