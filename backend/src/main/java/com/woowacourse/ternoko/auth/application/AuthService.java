package com.woowacourse.ternoko.auth.application;

import static com.woowacourse.ternoko.core.domain.member.MemberType.COACH;
import static com.woowacourse.ternoko.core.domain.member.MemberType.CREW;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.ternoko.auth.dto.response.LoginResponse;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.exception.TokenInvalidException;
import com.woowacourse.ternoko.core.domain.member.Member;
import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.coach.CoachRepository;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.domain.member.crew.CrewRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private static final String WOOWAHAN_COACH_EMAIL = "woowahan.com";
    private static final String TERNOKO_EMAIL = "ternoko.official@gmail.com";

    private final MethodsClientImpl slackMethodClient;

    private final MemberRepository memberRepository;
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;
    private final JwtProvider jwtProvider;

    private final String clientId;
    private final String clientSecret;

    public AuthService(final MethodsClientImpl slackMethodClient,
                       final MemberRepository memberRepository,
                       final CoachRepository coachRepository,
                       final CrewRepository crewRepository,
                       final JwtProvider jwtProvider,
                       @Value("${slack.clientId}") final String clientId,
                       @Value("${slack.clientSecret}") final String clientSecret) {
        this.slackMethodClient = slackMethodClient;
        this.memberRepository = memberRepository;
        this.coachRepository = coachRepository;
        this.crewRepository = crewRepository;
        this.jwtProvider = jwtProvider;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public LoginResponse login(final String code, String redirectUrl) throws SlackApiException, IOException {
        final OpenIDConnectUserInfoResponse userInfoResponse = getUserInfoResponseBySlack(code, redirectUrl);
        final Optional<Member> member = memberRepository.findByEmail(userInfoResponse.getEmail());
        if (member.isEmpty()) {
            return signUp(userInfoResponse);
        }

        boolean hasNickname = member.get().getNickname() != null;

        if (coachRepository.findById(member.get().getId()).isPresent()) {
            return LoginResponse.of(COACH, jwtProvider.createToken(member.get()),
                    hasNickname);
        }

        return LoginResponse.of(CREW, jwtProvider.createToken(member.get()),
                hasNickname);
    }

    private OpenIDConnectUserInfoResponse getUserInfoResponseBySlack(final String code, final String redirectUrl)
            throws IOException, SlackApiException {
        final OpenIDConnectTokenResponse openIDConnectTokenResponse = getOpenIDTokenResponse(code, redirectUrl);
        final OpenIDConnectUserInfoRequest userInfoRequest = OpenIDConnectUserInfoRequest.builder()
                .token(openIDConnectTokenResponse.getAccessToken())
                .build();
        return slackMethodClient.openIDConnectUserInfo(userInfoRequest);
    }

    private OpenIDConnectTokenResponse getOpenIDTokenResponse(final String code, final String redirectUrl)
            throws IOException, SlackApiException {
        final OpenIDConnectTokenRequest tokenRequest = OpenIDConnectTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUrl)
                .build();
        return slackMethodClient.openIDConnectToken(tokenRequest);
    }

    private LoginResponse signUp(final OpenIDConnectUserInfoResponse userInfoResponse) {
        if (hasCoachEmailSuffix(userInfoResponse) || isAdministratorEmail(userInfoResponse)) {
            final Coach coach = coachRepository.save(new Coach(userInfoResponse.getName(),
                    userInfoResponse.getEmail(),
                    userInfoResponse.getUserId(),
                    userInfoResponse.getUserImage192()));
            return LoginResponse.of(COACH, jwtProvider.createToken(coach), false);
        }
        final Crew crew = crewRepository.save(new Crew(userInfoResponse.getName(), userInfoResponse.getEmail(),
                userInfoResponse.getUserId(), userInfoResponse.getUserImage192()));

        return LoginResponse.of(CREW, jwtProvider.createToken(crew), false);
    }

    private boolean hasCoachEmailSuffix(final OpenIDConnectUserInfoResponse userInfoResponse) {
        return userInfoResponse.getEmail().contains(WOOWAHAN_COACH_EMAIL);
    }

    private boolean isAdministratorEmail(final OpenIDConnectUserInfoResponse userInfoResponse) {
        return userInfoResponse.getEmail()
                .equals(TERNOKO_EMAIL);
    }

    public boolean isValid(final String header) {
        final String token = AuthorizationExtractor.extract(header);
        jwtProvider.validateToken(token);
        return true;
    }

    public void checkMemberType(final Long id, final String type) {
        validateType(type);
        validateCoachTypeByMemberId(id, type);
        validateCrewTypeByMemberId(id, type);
    }

    private void validateType(String type) {
        if (!MemberType.existType(type)) {
            throw new TokenInvalidException(ExceptionType.INVALID_TOKEN);
        }
    }

    private void validateCoachTypeByMemberId(final Long id, final String type) {
        if (COACH.matchType(type) && !coachRepository.existsById(id)) {
            throw new TokenInvalidException(ExceptionType.INVALID_TOKEN);
        }
    }

    private void validateCrewTypeByMemberId(final Long id, final String type) {
        if (CREW.matchType(type) && !crewRepository.existsById(id)) {
            throw new TokenInvalidException(ExceptionType.INVALID_TOKEN);
        }
    }
}
