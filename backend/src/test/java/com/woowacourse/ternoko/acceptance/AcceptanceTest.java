package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.ReservationFixture.AFTER_TWO_DAYS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;

import com.woowacourse.ternoko.dto.request.ReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AcceptanceTest {

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

    protected ExtractableResponse<Response> patch(final String uri, final Header header, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(body)
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

    protected ExtractableResponse<Response> createReservation(final Long coachId, final String crewName) {
        final ReservationRequest reservationRequest = new ReservationRequest(crewName,
                AFTER_TWO_DAYS,
                FORM_ITEM_REQUESTS);

        return post("/api/reservations/coaches/" + coachId, reservationRequest);
    }
}
