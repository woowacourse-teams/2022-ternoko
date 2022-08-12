package com.woowacourse.ternoko.acceptance;

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
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW3;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW4;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.interview.dto.ScheduleResponse;
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
        // given
        put("/api/calendar/times", generateHeader(COACH4.getId()), MONTHS_REQUEST);
        createInterview(CREW1.getId(), COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createInterview(CREW2.getId(), COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME));
        createInterview(CREW3.getId(), COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME));
        createInterview(CREW4.getId(), COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("year", NOW.getYear())
                .queryParam("month", NOW.getMonthValue())
                .header(generateHeader(COACH4.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/schedules")
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
        final ExtractableResponse<Response> calendarResponse = put("/api/calendar/times",
                generateHeader(COACH1.getId()),
                MONTH_REQUEST);

        // then
        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다. - 여러 달")
    void saveCalendarsTimes() {
        // given & when
        final ExtractableResponse<Response> calendarResponse = put("/api/calendar/times",
                generateHeader(COACH3.getId()),
                MONTHS_REQUEST);

        // then
        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("선택한 년, 월의 면담 예약 내역 목록을 조회한다.")
    void findAllByCoach() {
        // given
        put("/api/calendar/times", generateHeader(COACH3.getId()), MONTHS_REQUEST);
        createInterview(CREW1.getId(), COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME));

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("year", NOW.getYear())
                .queryParam("month", NOW.getMonthValue())
                .header(generateHeader(COACH3.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/schedules")
                .then().log().all()
                .extract();
        final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);

        // then
        assertThat(scheduleResponse.getCalendar()).hasSize(1);
    }
}
