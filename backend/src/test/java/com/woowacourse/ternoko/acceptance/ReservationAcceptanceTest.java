package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_3_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW3;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import com.woowacourse.ternoko.interview.dto.InterviewResponse;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ReservationAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("면담 예약을 생성한다.")
    void create() {
        // given
        put("/api/calendar/times", generateHeader(COACH3.getId()), MONTHS_REQUEST);
        // when
        final ExtractableResponse<Response> response = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("선택한 일시가 코치의 가능한 시간이 아니라면 면담을 생성할 때 예외가 발생한다.")
    void create_WhenInvalidAvailableDateTime() {
        // given & when
        final ExtractableResponse<Response> response = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("면담 예약 상세 내역을 조회한다.")
    void findReservationById() {
        // given
        final Header header = generateHeader(COACH3.getId());
        put("/api/calendar/times", generateHeader(COACH3.getId()), MONTHS_REQUEST);
        final ExtractableResponse<Response> createdResponse = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = get(createdResponse.header("Location"), header);
        final InterviewResponse interviewResponse = response.body().as(InterviewResponse.class);

        // then
        assertAll(
                () -> assertThat(interviewResponse.getCoachNickname())
                        .isEqualTo(COACH3.getNickname()),
                () -> assertThat(interviewResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME)),
                () -> assertThat(interviewResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME).plusMinutes(INTERVIEW_TIME))
        );
    }

    @Test
    @DisplayName("크루 - 면담 예약 내역 목록을 조회한다.")
    void findAllByCrewId() {
        // given
        put("/api/calendar/times", generateHeader(COACH1.getId()), MONTHS_REQUEST);
        put("/api/calendar/times", generateHeader(COACH2.getId()), MONTHS_REQUEST);
        put("/api/calendar/times", generateHeader(COACH3.getId()), MONTHS_REQUEST);
        put("/api/calendar/times", generateHeader(COACH4.getId()), MONTHS_REQUEST);

        createReservation(CREW1.getId(), COACH1.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(CREW1.getId(), COACH2.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME));
        createReservation(CREW3.getId(), COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(CREW4.getId(), COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = get("/api/reservations", generateHeader(CREW1.getId()));
        final List<InterviewResponse> InterviewResponses = response.body().jsonPath()
                .getList(".", InterviewResponse.class);

        // then
        assertThat(InterviewResponses).hasSize(2);
    }


    @Test
    @DisplayName("크루가 면담 예약을 수정한다.")
    void updateReservation() {
        // given
        put("/api/calendar/times", generateHeader(COACH3.getId()), MONTHS_REQUEST);
        final ExtractableResponse<Response> response = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        final String redirectURI = response.header("Location");
        final char reservationId = redirectURI.charAt(redirectURI.length() - 1);

        // when
        final InterviewRequest updateRequest = new InterviewRequest(COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME), FORM_ITEM_REQUESTS);
        ExtractableResponse<Response> updateResponse = put("/api/reservations/" + reservationId,
                generateHeader(CREW1.getId()),
                updateRequest);

        //then
        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("크루가 면담 예약을 삭제한다.")
    void deleteReservation() {
        // given
        put("/api/calendar/times", generateHeader(COACH3.getId()), MONTHS_REQUEST);
        final ExtractableResponse<Response> response = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        final String redirectURI = response.header("Location");
        final char reservationId = redirectURI.charAt(redirectURI.length() - 1);

        // when
        final ExtractableResponse<Response> deleteResponse = delete("/api/reservations/" + reservationId,
                generateHeader(CREW1.getId()));

        //then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("코치가 면담 예약을 취소한다.")
    void cancelReservation() {
        // given
        put("/api/calendar/times", generateHeader(COACH3.getId()), MONTHS_REQUEST);
        final ExtractableResponse<Response> response = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        final String redirectURI = response.header("Location");
        final char reservationId = redirectURI.charAt(redirectURI.length() - 1);

        // when
        final ExtractableResponse<Response> cancelResponse = patch("/api/reservations/" + reservationId,
                generateHeader(COACH3.getId()));

        //then
        assertThat(cancelResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
