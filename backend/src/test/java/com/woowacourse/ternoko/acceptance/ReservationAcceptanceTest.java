package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW3;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.dto.ReservationResponse;
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
        put("/api/coaches/" + COACH3.getId() + "/calendar/times", MONTHS_REQUEST);
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
        // given
        // when
        final ExtractableResponse<Response> response = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("면담 예약 상세 내역을 조회한다.")
    void findReservationById() {
        // given
        final Header header = new Header(AUTHORIZATION,
                BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH3.getId())));
        put("/api/coaches/" + COACH3.getId() + "/calendar/times", header, MONTHS_REQUEST);
        final ExtractableResponse<Response> createdResponse = createReservation(CREW1.getId(), COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = get(createdResponse.header("Location"), header);
        final ReservationResponse reservationResponse = response.body().as(ReservationResponse.class);

        // then
        assertAll(
                () -> assertThat(reservationResponse.getCoachNickname())
                        .isEqualTo(COACH3.getNickname()),
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
        put("/api/coaches/" + COACH1.getId() + "/calendar/times", MONTHS_REQUEST);
        put("/api/coaches/" + COACH2.getId() + "/calendar/times", MONTHS_REQUEST);
        put("/api/coaches/" + COACH3.getId() + "/calendar/times", MONTHS_REQUEST);
        put("/api/coaches/" + COACH4.getId() + "/calendar/times", MONTHS_REQUEST);

        createReservation(CREW1.getId(), COACH1.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(CREW2.getId(), COACH2.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(CREW3.getId(), COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        createReservation(CREW4.getId(), COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));

        // when
        final ExtractableResponse<Response> response = get("/api/reservations");
        final List<ReservationResponse> reservationResponses = response.body().jsonPath()
                .getList(".", ReservationResponse.class);

        // then
        assertThat(reservationResponses).hasSize(4);
    }
}
