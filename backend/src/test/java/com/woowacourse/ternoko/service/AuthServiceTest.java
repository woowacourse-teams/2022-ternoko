package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_TOKEN;
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
import com.woowacourse.ternoko.common.exception.InvalidTokenException;
import com.woowacourse.ternoko.domain.Type;
import com.woowacourse.ternoko.dto.LoginResponse;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private MethodsClientImpl slackMethodClient;

    @SpyBean
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("코치가 본인인지 요청을 보내면 true 를 반환한다.")
    void checkCrewSameType() {
        Assertions.assertThatNoException().isThrownBy(() -> authService.checkMemberType(1L, "COACH"));
    }

    @Test
    @DisplayName("코치가 아닌데 코치로 요청을 보내면 에러를 반환한다.")
    void checkCrewSameType_false() {
        final Long crewId = 7L;
        // when
        Assertions.assertThatThrownBy(() -> authService.checkMemberType(7L, "COACH"))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessage(INVALID_TOKEN.getMessage());
    }

    @Test
    @DisplayName("로그인을 하면 AccessToken 을 발급한다.")
    void login() throws SlackApiException, IOException {
        // given
        setSlackMockData("sudal@gmail.com");
        // when
        final LoginResponse loginResponse = authService.login("temp_code", "temp_redirectUrl");
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
        final LoginResponse loginResponse = authService.login("temp_code", "temp_redirectUrl");
        assertThat(loginResponse.getMemberRole()).isEqualTo(Type.COACH);
    }


    @DisplayName("크루가 최초 로그인 시도시, 크루로 회원가입이 된다.")
    @Test
    void signup_crew() throws SlackApiException, IOException {
        // given
        final String crewEmail = "sudal1@naver.com";
        setSlackMockData(crewEmail);
        // when
        final LoginResponse loginResponse = authService.login("temp_code", "temp_redirectUrl");
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
        final LoginResponse loginResponse = authService.login("temp_code", "temp_redirectUrl");
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
        final LoginResponse loginResponse = authService.login("temp_code", "temp_redirectUrl");
        // then
        assertThat(loginResponse.isHasNickname()).isTrue();
    }


    private void setSlackMockData(String userEmail) throws IOException, SlackApiException {

        final OpenIDConnectTokenResponse openIDConnectTokenResponse = new OpenIDConnectTokenResponse();
        openIDConnectTokenResponse.setAccessToken("temp_accessToken");

        final OpenIDConnectUserInfoResponse userInfoResponse = new OpenIDConnectUserInfoResponse();
        userInfoResponse.setEmail(userEmail);
        userInfoResponse.setName("sudal");
        userInfoResponse.setUserImage192("testImage");

        given(slackMethodClient.openIDConnectToken(any(OpenIDConnectTokenRequest.class))).willReturn(
                openIDConnectTokenResponse);
        given(slackMethodClient.openIDConnectUserInfo(any(OpenIDConnectUserInfoRequest.class))).willReturn(
                userInfoResponse);
        given(jwtProvider.createToken("1")).willReturn("temp_access_token");
    }
}
