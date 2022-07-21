package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
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
                .queryParam("year", NOW_PLUS_2_DAYS.getYear())
                .queryParam("month", NOW_PLUS_2_DAYS.getMonthValue())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/coaches/" + COACH3.getId() + "/schedules")
                .then().log().all()
                .extract();
        final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);

        // then
        assertThat(scheduleResponse.getCalendar()).hasSize(1);
    }
}
