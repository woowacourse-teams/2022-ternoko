package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEM_REQUESTS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.woowacourse.ternoko.auth.application.JwtProvider;
import com.woowacourse.ternoko.core.domain.member.Member;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.dto.request.InterviewRequest;
import com.woowacourse.ternoko.support.SlackAlarm;
import com.woowacourse.ternoko.support.utils.AcceptanceTest;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AcceptanceTest
public class AcceptanceSupporter extends DatabaseSupporter {

    @Autowired
    public JwtProvider jwtProvider;

    @MockBean
    private SlackAlarm slackAlarm;

    @MockBean
    private WebClient webClient;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.RequestBodySpec requestBodySpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        given(webClient.post()).willReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(), any())).thenReturn(requestBodySpec);
        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);
        when(responseSpec.bodyToMono(ArgumentMatchers.<Class<String>>notNull()))
                .thenReturn(Mono.just("resp"));
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);

        when(requestBodySpec.retrieve()).thenReturn(responseSpec);

        doNothing().when(slackAlarm).sendCreateMessage(any());
        doNothing().when(slackAlarm).sendUpdateMessage(any(), any());
        doNothing().when(slackAlarm).sendCancelMessage(any());
        doNothing().when(slackAlarm).sendDeleteMessage(any());
    }

    protected ExtractableResponse<Response> post(final String uri, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> post(final String uri, final Header header, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(body)
                .when().post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(final String uri) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(final String uri, final Header header) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> put(final String uri, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> put(final String uri, final Header header, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(body)
                .when().put(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> patch(final String uri, final Header header) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .when().patch(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> patch(final String uri, final Header header, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(body)
                .when().patch(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> patchInterview(final String uri, final Header header,
                                                           final boolean onlyInterview) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .queryParam("onlyInterview", onlyInterview)
                .when().patch(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> delete(final String uri, final Header header) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .when().delete(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> createInterview(final Crew crew,
                                                            final Long coachId,
                                                            final LocalDateTime interviewDateTime) {
        final InterviewRequest interviewRequest = new InterviewRequest(coachId, interviewDateTime,
                FORM_ITEM_REQUESTS);

        return post("/api/interviews/", generateHeader(crew), interviewRequest);
    }

    protected Header generateHeader(final Member member) {
        return new Header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(member));
    }

    protected Long parseLocationHeader(final ExtractableResponse<Response> response, final String regex) {
        return Long.parseLong(response.header("Location").split(regex)[1]);
    }
}
