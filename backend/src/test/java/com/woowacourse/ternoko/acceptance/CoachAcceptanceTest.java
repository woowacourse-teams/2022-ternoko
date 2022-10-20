//package com.woowacourse.ternoko.acceptance;
//
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME_COACH1_1;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME_COACH1_2;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME_COACH1_3;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME_COACH1_4;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH3;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW3;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW4;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.woowacourse.ternoko.core.dto.response.ScheduleResponse;
//import io.restassured.RestAssured;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//public class CoachAcceptanceTest extends AcceptanceSupporter {
//
//    @Test
//    @DisplayName("코치 - 면담 예약 내역 목록을 조회한다.")
//    void findAllByCoaches() {
//        // given
//        put("/api/calendar/times", generateHeader(COACH1), MONTHS_REQUEST);
//        createInterviewByCoach1(CREW1, AVAILABLE_DATE_TIME_COACH1_4);
//        createInterviewByCoach1(CREW2, AVAILABLE_DATE_TIME_COACH1_3);
//        createInterviewByCoach1(CREW3, AVAILABLE_DATE_TIME_COACH1_2);
//        createInterviewByCoach1(CREW4, AVAILABLE_DATE_TIME_COACH1_1);
//
//        // when
//        final ExtractableResponse<Response> response = RestAssured.given().log().all()
//                .queryParam("year", NOW.getYear())
//                .queryParam("month", NOW.getMonthValue())
//                .header(generateHeader(COACH1))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/api/schedules")
//                .then().log().all()
//                .extract();
//        final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);
//
//        // then
//        assertThat(scheduleResponse.getCalendar()).hasSize(4);
//    }
//
//    @Test
//    @DisplayName("코치의 면담 가능 시간을 저장한다.")
//    void saveCalendarTimes() {
//        // given & when
//        final ExtractableResponse<Response> calendarResponse = put("/api/calendar/times",
//                generateHeader(COACH1),
//                MONTH_REQUEST);
//
//        // then
//        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    @Test
//    @DisplayName("코치의 면담 가능 시간을 저장한다. - 여러 달")
//    void saveCalendarsTimes() {
//        // given & when
//        final ExtractableResponse<Response> calendarResponse = put("/api/calendar/times",
//                generateHeader(COACH1),
//                MONTHS_REQUEST);
//
//        // then
//        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    @Test
//    @DisplayName("선택한 년, 월의 면담 예약 내역 목록을 조회한다.")
//    void findAllByCoach() {
//        // given
//        put("/api/calendar/times", generateHeader(COACH3), MONTHS_REQUEST);
//        createInterviewByCoach1(CREW1, AVAILABLE_DATE_TIME_COACH1_1);
//
//        // when
//        final ExtractableResponse<Response> response = RestAssured.given().log().all()
//                .queryParam("year", NOW.getYear())
//                .queryParam("month", NOW.getMonthValue())
//                .header(generateHeader(COACH1))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/api/schedules")
//                .then().log().all()
//                .extract();
//        final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);
//
//        // then
//        assertThat(scheduleResponse.getCalendar()).hasSize(1);
//    }
//}
