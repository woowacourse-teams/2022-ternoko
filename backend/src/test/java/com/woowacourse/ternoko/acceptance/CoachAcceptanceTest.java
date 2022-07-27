package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_3_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.AFTER_TWO_DAYS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CoachAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("코치 - 면담 예약 내역 목록을 조회한다.")
    void findAllByCoaches() {
        // :todo 현재는 코치 값으로 accessToken 발급 중. 추후 크루 값으로 변경해야 함.
        // given
        put("/api/coaches/" + COACH4.getId() + "/calendar/times", MONTHS_REQUEST);
        createReservation(COACH4.getId(), "애쉬", LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(COACH4.getId(), "바니", LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME));
        createReservation(COACH4.getId(), "앤지", LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME));
        createReservation(COACH4.getId(), "열음", LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("year", AFTER_TWO_DAYS.getYear())
                .queryParam("month", AFTER_TWO_DAYS.getMonthValue())
                .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH4.getId())))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/coaches/" + COACH4.getId() + "/schedules")
                .then().log().all()
                .extract();
        final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);

        // then
        assertThat(scheduleResponse.getCalendar()).hasSize(4);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void saveCalendarTimes() {
        // given & when
        final ExtractableResponse<Response> calendarResponse = put("/api/coaches/" + COACH3.getId() + "/calendar/times",
                MONTH_REQUEST);

        // then
        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다. - 여러 달")
    void saveCalendarsTimes() {
        // given & when
        final ExtractableResponse<Response> calendarResponse = put("/api/coaches/" + COACH3.getId() + "/calendar/times",
                MONTHS_REQUEST);

        // then
        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("선택한 년, 월의 면담 예약 내역 목록을 조회한다.")
    void findAllByCoach() {
        // given
        put("/api/coaches/" + COACH3.getId() + "/calendar/times", MONTHS_REQUEST);

        final ReservationRequest reservationRequest = new ReservationRequest("바니",
                LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                FORM_ITEM_REQUESTS);
        post("/api/reservations/coaches/" + COACH3.getId(), reservationRequest);

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("year", NOW.getYear())
                .queryParam("month", NOW.getMonthValue())
                .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/coaches/" + COACH3.getId() + "/schedules")
                .then().log().all()
                .extract();
        final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);

        // then
        assertThat(scheduleResponse.getCalendar()).hasSize(1);
    }
}
