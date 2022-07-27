package com.woowacourse.ternoko.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.ternoko.common.JwtProvider;
import com.woowacourse.ternoko.domain.Type;
import com.woowacourse.ternoko.dto.LoginResponse;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @MockBean
    MethodsClientImpl slackMethodClient;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    CrewRepository crewRepository;
    @SpyBean
    JwtProvider jwtProvider;

    @Test
    @DisplayName("로그인을 하면 AccessToken 을 발급한다.")
    void login() throws SlackApiException, IOException {
        // given
        setSlackMockData("sudal@gmail.com");
        // when
        final LoginResponse loginResponse = authService.login("temp_code");
        // then
        assertThat(loginResponse.getAccessToken()).isNotNull();
    }

    @DisplayName("코치가 최초 로그인 시도시, 코치로 회원가입이 된다.")
    @Test
    void signup_coach() throws SlackApiException, IOException {
        // given
        final String coachEmail = "pobi@woowahan.com";
        setSlackMockData(coachEmail);
        // when
        final LoginResponse loginResponse = authService.login("temp_code");
        assertThat(loginResponse.getMemberRole()).isEqualTo(Type.COACH);
    }


    @DisplayName("크루가 최초 로그인 시도시, 크루로 회원가입이 된다.")
    @Test
    void signup_crew() throws SlackApiException, IOException {
        // given
        final String crewEmail = "sudal1@naver.com";
        setSlackMockData(crewEmail);
        // when
        final LoginResponse loginResponse = authService.login("temp_code");
        // then
        assertThat(loginResponse.getMemberRole()).isEqualTo(Type.CREW);
    }

    @DisplayName("크루가 최초 회원가입 시, 닉네임을 입력 받아야한다.")
    @Test
    void signup_crew_with_nickname() throws SlackApiException, IOException {
        // given
        final String crewEmail = "sudal@naver.com";
        setSlackMockData(crewEmail);
        // when
        final LoginResponse loginResponse = authService.login("temp_code");
        // then
        assertThat(loginResponse.isHasNickname()).isFalse();
    }

    @DisplayName("기존에 있는 회원일시, 닉네임을 입력받지 않고 바로 로그인한다.")
    @Test
    void direct_login() throws SlackApiException, IOException {
        // given
        final String coachEmail = "test1@woowahan.com";
        setSlackMockData(coachEmail);
        // when
        final LoginResponse loginResponse = authService.login("temp_code");
        // then
        assertThat(loginResponse.isHasNickname()).isTrue();
    }


    private void setSlackMockData(String userEmail) throws IOException, SlackApiException {

        OpenIDConnectTokenResponse openIDConnectTokenResponse = new OpenIDConnectTokenResponse();
        openIDConnectTokenResponse.setAccessToken("temp_accessToken");

        final OpenIDConnectUserInfoResponse userInfoResponse = new OpenIDConnectUserInfoResponse();
        userInfoResponse.setEmail(userEmail);
        userInfoResponse.setName("sudal");
        userInfoResponse.setTeamImage230("testImage");

        given(slackMethodClient.openIDConnectToken(any(OpenIDConnectTokenRequest.class))).willReturn(
                openIDConnectTokenResponse);
        given(slackMethodClient.openIDConnectUserInfo(any(OpenIDConnectUserInfoRequest.class))).willReturn(
                userInfoResponse);
        given(jwtProvider.createToken("1")).willReturn("temp_access_token");
    }
}
