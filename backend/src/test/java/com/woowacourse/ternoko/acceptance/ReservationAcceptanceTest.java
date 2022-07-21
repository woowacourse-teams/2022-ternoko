package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.dto.ReservationResponse;
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
        // given, when
        final ExtractableResponse<Response> response = createReservation(COACH1.getId(),
                "애쉬",
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("면담 예약 상세 내역을 조회한다.")
    void findReservationById() {
        // given
        final ExtractableResponse<Response> createdResponse = createReservation(COACH1.getId(),
                "수달",
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = get(createdResponse.header("Location"));
        final ReservationResponse reservationResponse = response.body().as(ReservationResponse.class);

        // then
        assertAll(
                () -> assertThat(reservationResponse.getCoachNickname())
                        .isEqualTo(COACH1.getNickname()),
                () -> assertThat(reservationResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME)),
                () -> assertThat(reservationResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME).plusMinutes(INTERVIEW_TIME))
        );
    }

    @Test
    @DisplayName("면담 예약 내역 목록을 조회한다.")
    void findAll() {
        // given
        createReservation(COACH1.getId(), "애쉬", LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(COACH2.getId(), "바니", LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(COACH3.getId(), "앤지", LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(COACH4.getId(), "열음", LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = get("/api/reservations");
        final List<ReservationResponse> reservationResponses = response.body().jsonPath()
                .getList(".", ReservationResponse.class);

        // then
        assertThat(reservationResponses).hasSize(4);
    }
}
