package com.woowacourse.ternoko.service;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.ternoko.common.JwtProvider;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.io.IOException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MethodsClientImpl slackMethodClient;

    private final MemberRepository memberRepository;
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;
    private final JwtProvider jwtProvider;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;

    public AuthService(MethodsClientImpl slackMethodClient,
                       MemberRepository memberRepository,
                       CoachRepository coachRepository,
                       CrewRepository crewRepository,
                       JwtProvider jwtProvider,
                       @Value("${slack.clientId}") final String clientId,
                       @Value("${slack.clientSecret}") final String clientSecret,
                       @Value("${slack.redirectUrl}") final String redirectUrl) {
        this.slackMethodClient = slackMethodClient;
        this.memberRepository = memberRepository;
        this.coachRepository = coachRepository;
        this.crewRepository = crewRepository;
        this.jwtProvider = jwtProvider;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
    }

    public String login(final String code) throws SlackApiException, IOException {
        final OpenIDConnectUserInfoResponse userInfoResponse = getUserInfoResponseBySlack(code);
        // 로그인인지, 회원 가입인지 갈래
        System.out.println("userInfoResponse Email : "+ userInfoResponse.getEmail());
        final Optional<Member> member = memberRepository.findByEmail(userInfoResponse.getEmail());
        System.out.println("test");
        if (member.isPresent()) {
            return jwtProvider.createToken(String.valueOf(member.get().getId()));
        }

        //회원 가입
        return signUp(userInfoResponse);
    }

    private OpenIDConnectUserInfoResponse getUserInfoResponseBySlack(String code)
            throws IOException, SlackApiException {
        final OpenIDConnectTokenResponse openIDConnectTokenResponse = getOpenIDTokenResponse(
                code);
        System.out.println("accessToken : "+ openIDConnectTokenResponse.getAccessToken());
        final OpenIDConnectUserInfoRequest userInfoRequest = OpenIDConnectUserInfoRequest.builder()
                .token(openIDConnectTokenResponse.getAccessToken())
                .build();
        final OpenIDConnectUserInfoResponse userInfoResponse = slackMethodClient.openIDConnectUserInfo(
                userInfoRequest);
        return userInfoResponse;
    }

    private OpenIDConnectTokenResponse getOpenIDTokenResponse(String code)
            throws IOException, SlackApiException {
        final OpenIDConnectTokenRequest tokenRequest = OpenIDConnectTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUrl)
                .build();
        final OpenIDConnectTokenResponse openIDConnectTokenResponse = slackMethodClient.openIDConnectToken(
                tokenRequest);
        return openIDConnectTokenResponse;
    }

    @NotNull
    private String signUp(OpenIDConnectUserInfoResponse userInfoResponse) {
        if (userInfoResponse.getEmail().contains("woowahan.com")) {
            final Coach coach = coachRepository.save(new Coach(userInfoResponse.getName(), userInfoResponse.getEmail(),
                    userInfoResponse.getTeamImage230()));
            return jwtProvider.createToken(String.valueOf(coach.getId()));
        }

        final Crew crew = crewRepository.save(new Crew(userInfoResponse.getName(), userInfoResponse.getEmail(),
                userInfoResponse.getTeamImage230()));

        return jwtProvider.createToken(String.valueOf(crew.getId()));
    }
}
