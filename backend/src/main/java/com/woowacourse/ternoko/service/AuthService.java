package com.woowacourse.ternoko.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final MethodsClientImpl SLACK_METHODS_CLIENT = new MethodsClientImpl(new Slack().getHttpClient());

    private final MemberRepository memberRepository;
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;

    public AuthService(MemberRepository memberRepository,
                       CoachRepository coachRepository,
                       CrewRepository crewRepository,
                       @Value("${slack.clientId}") final String clientId,
                       @Value("${slack.clientSecret}") final String clientSecret,
                       @Value("${slack.redirectUrl}") final String redirectUrl) {
        this.memberRepository = memberRepository;
        this.coachRepository = coachRepository;
        this.crewRepository = crewRepository;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
    }

    public String login(final String code) throws SlackApiException, IOException {
        final OpenIDConnectUserInfoResponse userInfoResponse = getUserInfoResponseBySlack(code);
        // 로그인인지, 회원 가입인지 갈래
        if (memberRepository.findByEmail(userInfoResponse.getEmail()).isPresent()) {
            // 로그인
            return "accessToken";
        }

        //회원 가입
        return signUp(userInfoResponse);
    }

    @NotNull
    private String signUp(OpenIDConnectUserInfoResponse userInfoResponse) {
        if (userInfoResponse.getEmail().contains("woowahan.com")) {
            final Coach coach = coachRepository.save(new Coach(userInfoResponse.getName(), userInfoResponse.getEmail(),
                    userInfoResponse.getTeamImage230()));
            return "accessToken";
        }

        final Crew crew = crewRepository.save(new Crew(userInfoResponse.getName(), userInfoResponse.getEmail(),
                userInfoResponse.getTeamImage230()));

        return "accessToken";
    }

    private OpenIDConnectTokenResponse getOpenIDTokenResponse(String code)
            throws IOException, SlackApiException {
        final OpenIDConnectTokenRequest tokenRequest = OpenIDConnectTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUrl)
                .build();

        final OpenIDConnectTokenResponse openIDConnectTokenResponse = SLACK_METHODS_CLIENT.openIDConnectToken(
                tokenRequest);
        return openIDConnectTokenResponse;
    }

    private OpenIDConnectUserInfoResponse getUserInfoResponseBySlack(String code)
            throws IOException, SlackApiException {
        final OpenIDConnectTokenResponse openIDConnectTokenResponse = getOpenIDTokenResponse(
                code);

        final OpenIDConnectUserInfoRequest userInfoRequest = OpenIDConnectUserInfoRequest.builder()
                .token(openIDConnectTokenResponse.getAccessToken())
                .build();

        final OpenIDConnectUserInfoResponse userInfoResponse = SLACK_METHODS_CLIENT.openIDConnectUserInfo(
                userInfoRequest);
        return userInfoResponse;
    }
}
