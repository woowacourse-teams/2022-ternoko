package com.woowacourse.ternoko.api.restdocs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.ternoko.comment.application.CommentService;
import com.woowacourse.ternoko.common.log.CustomLoggingFilter;
import com.woowacourse.ternoko.common.log.LogInterceptor;
import com.woowacourse.ternoko.config.WebConfig;
import com.woowacourse.ternoko.interview.application.InterviewService;
import com.woowacourse.ternoko.login.aop.LoginMemberVerifier;
import com.woowacourse.ternoko.login.aop.MemberTypeCache;
import com.woowacourse.ternoko.login.application.AuthService;
import com.woowacourse.ternoko.login.application.JwtProvider;
import com.woowacourse.ternoko.login.presentation.AuthenticationPrincipalConfig;
import com.woowacourse.ternoko.service.CoachService;
import com.woowacourse.ternoko.service.CrewService;
import com.woowacourse.ternoko.service.EmailService;
import com.woowacourse.ternoko.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
@Import({RestDocsConfiguration.class, MemberTypeCache.class, LoginMemberVerifier.class, WebConfig.class,
        AuthenticationPrincipalConfig.class, JwtProvider.class})
@ExtendWith(value = {RestDocumentationExtension.class})
public class RestDocsTestSupporter {

    @BeforeEach
    void setUp() {
        given(authService.isValid(any())).willReturn(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    protected JwtProvider jwtProvider;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected CommentService commentService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected CrewService crewService;

    @MockBean
    protected CoachService coachService;

    @MockBean
    protected InterviewService interviewService;

    @MockBean
    protected EmailService emailService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected LogInterceptor logInterceptor;

    @Autowired
    protected CustomLoggingFilter customLoggingFilter;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @BeforeEach
    void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .build();
    }
}
