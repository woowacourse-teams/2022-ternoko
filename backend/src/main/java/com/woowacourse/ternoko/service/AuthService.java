package com.woowacourse.ternoko.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.ternoko.common.JwtProvider;
import com.woowacourse.ternoko.config.AuthorizationExtractor;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;

@Service
@Transactional
public class AuthService {

	private final MethodsClientImpl slackMethodClient;

	private final MemberRepository memberRepository;
	private final CoachRepository coachRepository;
	private final CrewRepository crewRepository;
	private final JwtProvider jwtProvider;

	private final String clientId;
	private final String clientSecret;
	private final String redirectUrl;

	public AuthService(final MethodsClientImpl slackMethodClient,
		final MemberRepository memberRepository,
		final CoachRepository coachRepository,
		final CrewRepository crewRepository,
		final JwtProvider jwtProvider,
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
		final Optional<Member> member = memberRepository.findByEmail(userInfoResponse.getEmail());
		if (member.isPresent()) {
			return jwtProvider.createToken(String.valueOf(member.get().getId()));
		}
		return signUp(userInfoResponse);
	}

	private OpenIDConnectUserInfoResponse getUserInfoResponseBySlack(final String code)
		throws IOException, SlackApiException {
		final OpenIDConnectTokenResponse openIDConnectTokenResponse = getOpenIDTokenResponse(
			code);
		final OpenIDConnectUserInfoRequest userInfoRequest = OpenIDConnectUserInfoRequest.builder()
			.token(openIDConnectTokenResponse.getAccessToken())
			.build();
		return slackMethodClient.openIDConnectUserInfo(userInfoRequest);
	}

	private OpenIDConnectTokenResponse getOpenIDTokenResponse(final String code)
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

	private String signUp(final OpenIDConnectUserInfoResponse userInfoResponse) {
		if (userInfoResponse.getEmail().contains("woowahan.com")) {
			final Coach coach = coachRepository.save(new Coach(userInfoResponse.getName(), userInfoResponse.getEmail(),
				userInfoResponse.getTeamImage230()));
			return jwtProvider.createToken(String.valueOf(coach.getId()));
		}

		final Crew crew = crewRepository.save(new Crew(userInfoResponse.getName(), userInfoResponse.getEmail(),
			userInfoResponse.getTeamImage230()));

		return jwtProvider.createToken(String.valueOf(crew.getId()));
	}

	public boolean isValid(final String header) {
		String token = AuthorizationExtractor.extract(header);
		jwtProvider.validateToken(token);
		return true;
	}
}
