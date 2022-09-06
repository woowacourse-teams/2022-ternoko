package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class InterviewAcceptanceTest extends AcceptanceSupporter {

    @Test
    @DisplayName("면담 예약을 생성한다.")
    void create() {
        // given
        put("/api/calendar/times", generateHeader(COACH3), MONTHS_REQUEST);
        // when
        final ExtractableResponse<Response> response = createInterview(CREW1, COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("선택한 일시가 코치의 가능한 시간이 아니라면 면담을 생성할 때 예외가 발생한다.")
    void create_WhenInvalidAvailableDateTime() {
        // given & when
        final ExtractableResponse<Response> response = createInterview(CREW1, COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

//    @Test
//    @DisplayName("면담 예약 상세 내역을 조회한다.")
//    void findInterviewById() {
//        // given
//        final Header header = generateHeader(COACH3);
//        put("/api/calendar/times", header, MONTHS_REQUEST);
//        final ExtractableResponse<Response> createdResponse = createInterview(CREW1, COACH3.getId(),
//                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
//
//        // when
//        final ExtractableResponse<Response> response = get(createdResponse.header("Location"), header);
//        final InterviewResponse interviewResponse = response.body().as(InterviewResponse.class);
//
//        // then
//        assertAll(
//                () -> assertThat(interviewResponse.getCoachNickname())
//                        .isEqualTo(COACH3.getNickname()),
//                () -> assertThat(interviewResponse.getInterviewStartTime())
//                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME)),
//                () -> assertThat(interviewResponse.getInterviewEndTime())
//                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME).plusMinutes(INTERVIEW_TIME))
//        );
//    }

//    @Test
//    @DisplayName("크루 - 면담 예약 내역 목록을 조회한다.")
//    void findAllByCrewId() {
//        // given
//        put("/api/calendar/times", generateHeader(COACH1), MONTHS_REQUEST);
//        put("/api/calendar/times", generateHeader(COACH2), MONTHS_REQUEST);
//        put("/api/calendar/times", generateHeader(COACH3), MONTHS_REQUEST);
//        put("/api/calendar/times", generateHeader(COACH4), MONTHS_REQUEST);
//
//        createInterview(CREW1, COACH2.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME));
//        createInterview(CREW1, COACH1.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
//        createInterview(CREW3, COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
//        createInterview(CREW4, COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
//
//        // when
//        final ExtractableResponse<Response> response = get("/api/interviews", generateHeader(CREW1));
//        final List<InterviewResponse> InterviewResponses = response.body().jsonPath()
//                .getList(".", InterviewResponse.class);
//
//        // then
//        assertThat(InterviewResponses).hasSize(2);
//    }

//    @Test
//    @DisplayName("크루가 면담 예약을 수정한다.")
//    void updateInterview() {
//        // given
//        put("/api/calendar/times", generateHeader(COACH3), MONTHS_REQUEST);
//        final ExtractableResponse<Response> response = createInterview(CREW1, COACH3.getId(),
//                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
//
//        final String redirectURI = response.header("Location");
//        final char interviewId = redirectURI.charAt(redirectURI.length() - 1);
//
//        // when
//        final InterviewRequest updateRequest = new InterviewRequest(COACH3.getId(),
//                LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME), FORM_ITEM_REQUESTS);
//        ExtractableResponse<Response> updateResponse = put("/api/interviews/" + interviewId,
//                generateHeader(CREW1), updateRequest);
//
//        //then
//        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    @Test
//    @DisplayName("크루가 면담 예약을 삭제한다.")
//    void deleteInterview() {
//        // given
//        put("/api/calendar/times", generateHeader(COACH3), MONTHS_REQUEST);
//        final ExtractableResponse<Response> response = createInterview(CREW1, COACH3.getId(),
//                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
//
//        final String redirectURI = response.header("Location");
//        final char interviewId = redirectURI.charAt(redirectURI.length() - 1);
//
//        // when
//        final ExtractableResponse<Response> deleteResponse = delete("/api/interviews/" + interviewId,
//                generateHeader(CREW1));
//
//        //then
//        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }

//    @Test
//    @DisplayName("코치가 면담 예약만 취소한다.")
//    void cancelInterview_onlyInterview() {
//        // given
//        put("/api/calendar/times", generateHeader(COACH3), MONTHS_REQUEST);
//        final ExtractableResponse<Response> response = createInterview(CREW1, COACH3.getId(),
//                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
//
//        final String redirectURI = response.header("Location");
//        final char interviewId = redirectURI.charAt(redirectURI.length() - 1);
//
//        // when
//        final ExtractableResponse<Response> cancelResponse = patchInterview("/api/interviews/" + interviewId,
//                generateHeader(COACH3), true);
//        //then
//        assertThat(cancelResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }

//    @Test
//    @DisplayName("코치가 면담 예약 취소 + 되는 시간을 삭제한다.")
//    void cancelInterview_with_availableDateTime() {
//        // given
//        final LocalDateTime interviewStartTime = LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME);
//
//        put("/api/calendar/times", generateHeader(COACH3), MONTHS_REQUEST);
//        final ExtractableResponse<Response> response = createInterview(CREW1, COACH3.getId(),
//                interviewStartTime);
//
//        final String redirectURI = response.header("Location");
//        final char interviewId = redirectURI.charAt(redirectURI.length() - 1);
//
//        // when
//        final ExtractableResponse<Response> cancelResponse = patchInterview("/api/interviews/" + interviewId,
//                generateHeader(COACH3), false);
//
//        final ExtractableResponse<Response> availableDateTimes = RestAssured.given().log().all()
//                .queryParam("coachId", COACH3.getId())
//                .queryParam("year", NOW_PLUS_2_DAYS.getYear())
//                .queryParam("month", NOW_PLUS_2_DAYS.getMonthValue())
//                .header(generateHeader(COACH3))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/api/calendar/times")
//                .then().log().all()
//                .extract();
//
//        final AvailableDateTimesResponse availableDateTimesResponse = availableDateTimes.body()
//                .as(AvailableDateTimesResponse.class);
//        //then
//        assertAll(
//                () -> assertThat(cancelResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
//
//                () -> assertThat(availableDateTimesResponse.getCalendarTimes()).extracting("calendarTime")
//                        .doesNotContain(interviewStartTime)
//        );
//    }
}
