package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.InterviewFixture.FORM_ITEM_REQUESTS;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.BEARER_TYPE;

import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import com.woowacourse.ternoko.login.application.JwtProvider;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AcceptanceTest {

    @Autowired
    public JwtProvider jwtProvider;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
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
