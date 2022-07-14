package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.AFTER_TWO_DAYS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
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
        final ExtractableResponse<Response> response = createReservation(COACH1.getId(), "애쉬");

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("면담 예약 상세 내역을 조회한다.")
    void findReservationById() {
        // given
        final ReservationRequest reservationRequest = new ReservationRequest("수달7",
                AFTER_TWO_DAYS,
                FORM_ITEM_REQUESTS);

        final ExtractableResponse<Response> createdResponse = post("/api/reservations/coaches/" + COACH3.getId(),
                reservationRequest);

        // when
        final ExtractableResponse<Response> response = get(createdResponse.header("Location"));
        final ReservationResponse reservationResponse = response.body().as(ReservationResponse.class);
        final LocalDateTime reservationDatetime = reservationRequest.getInterviewDatetime();

        // then
        assertAll(
                () -> assertThat(reservationResponse.getCoachNickname())
                        .isEqualTo(COACH3.getNickname()),
                () -> assertThat(reservationResponse.getInterviewStartTime())
                        .isEqualTo(reservationDatetime),
                () -> assertThat(reservationResponse.getInterviewEndTime())
                        .isEqualTo(reservationDatetime.plusMinutes(INTERVIEW_TIME))
        );
    }

    @Test
    @DisplayName("면담 예약 내역 목록을 조회한다.")
    void findAll() {
        // given
        createReservation(COACH1.getId(), "애쉬");
        createReservation(COACH2.getId(), "바니");
        createReservation(COACH3.getId(), "앤지");
        createReservation(COACH4.getId(), "열음");

        // when
        final ExtractableResponse<Response> response = get("/api/reservations");
        final List<ReservationResponse> reservationResponses = response.body().jsonPath()
                .getList(".", ReservationResponse.class);

        // then
        assertThat(reservationResponses).hasSize(4);
    }
}
