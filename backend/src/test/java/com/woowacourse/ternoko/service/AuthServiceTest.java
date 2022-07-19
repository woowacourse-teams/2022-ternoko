package com.woowacourse.ternoko.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.slack.api.util.http.SlackHttpClient;
import com.woowacourse.ternoko.common.JwtProvider;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private Slack Slack;
    @Mock
    private MethodsClientImpl methodsClient;

    @Mock
    private SlackHttpClient slackHttpClient;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CoachRepository coachRepository;
    @Mock
    private CrewRepository crewRepository;
    @Mock
    private JwtProvider jwtProvider;

    @DisplayName("회원 정보가 없는 회원이라면 회원 가입을 진행하고 AccessToken을 반환한다.")
    @Test
    void signUpBySlack() throws SlackApiException, IOException {
        // given
        String code = "temp_code";
        String clientId = "temp_clientId";
        String clientSecret = "temp_clientSecret";
        OpenIDConnectUserInfoResponse openIDConnectUserInfoResponse = new OpenIDConnectUserInfoResponse();
        openIDConnectUserInfoResponse.setEmail("test@woowahan.com");
        final OpenIDConnectTokenRequest tokenRequest = OpenIDConnectTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri("")
                .build();
        given(methodsClient.openIDConnectToken(tokenRequest))
                .willReturn(new OpenIDConnectTokenResponse());
        final OpenIDConnectUserInfoRequest userInfoRequest = OpenIDConnectUserInfoRequest.builder().token("token")
                .build();
        given(methodsClient.openIDConnectUserInfo(userInfoRequest))
                .willReturn(openIDConnectUserInfoResponse);
        given(memberRepository.findByEmail("email"))
                .willReturn(Optional.empty());
        // when
        final String accessToken = authService.login(code);
        // then
        assertThat(accessToken).isNotNull();

    }


}
