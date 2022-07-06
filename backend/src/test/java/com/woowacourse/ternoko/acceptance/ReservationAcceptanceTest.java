package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.domain.Location;
import com.woowacourse.ternoko.dto.FormItemRequest;
import com.woowacourse.ternoko.dto.ReservationRequest;
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
        // given
        final ReservationRequest reservationRequest = new ReservationRequest("바니",
                LocalDateTime.of(2022, 7, 4, 14, 0, 0),
                Location.JAMSIL.getValue(),
                List.of(new FormItemRequest("고정질문1", "답변1"),
                        new FormItemRequest("고정질문2", "답변2"),
                        new FormItemRequest("고정질문3", "답변3")));

        final Long coachId = 1L;

        // when
        final ExtractableResponse<Response> response = post("/api/reservations/coaches/" + coachId, reservationRequest);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("면담 예약 상세 내역을 조회한다.")
    void findReservationById() {
        // given
        final ReservationRequest reservationRequest = new ReservationRequest("바니",
                LocalDateTime.of(2022, 7, 4, 14, 0, 0),
                Location.JAMSIL.getValue(),
                List.of(new FormItemRequest("고정질문1", "답변1"),
                        new FormItemRequest("고정질문2", "답변2"),
                        new FormItemRequest("고정질문3", "답변3")));

        final ExtractableResponse<Response> createdResponse = post("/api/reservations/coaches/" + COACH1.getId(),
                reservationRequest);

        // when
        final ExtractableResponse<Response> response = get(createdResponse.header("Location"));
        final ReservationResponse reservationResponse = response.body().as(ReservationResponse.class);
        final LocalDateTime reservationDatetime = reservationRequest.getReservationDatetime();

        // then
        assertAll(
                () -> assertThat(reservationResponse.getCoachNickname())
                        .isEqualTo(COACH1.getNickname()),
                () -> assertThat(reservationResponse.getReservationDate())
                        .isEqualTo(reservationDatetime.toLocalDate()),
                () -> assertThat(reservationResponse.getReservationStartTime())
                        .isEqualTo(reservationDatetime.toLocalTime()),
                () -> assertThat(reservationResponse.getReservationEndTime())
                        .isEqualTo(reservationDatetime.plusMinutes(INTERVIEW_TIME).toLocalTime())
        );
    }
}
