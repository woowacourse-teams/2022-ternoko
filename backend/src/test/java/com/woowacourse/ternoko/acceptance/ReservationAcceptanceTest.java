package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH2;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH3;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.dto.FormItemDto;
import com.woowacourse.ternoko.dto.ReservationRequest;
import com.woowacourse.ternoko.dto.ReservationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                LocalDateTime.of(2022, 7, 4, 14, 0, 0),
                List.of(new FormItemDto("고정질문1", "답변1"),
                        new FormItemDto("고정질문2", "답변2"),
                        new FormItemDto("고정질문3", "답변3")));

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
                () -> assertThat(reservationResponse.getInterviewDate())
                        .isEqualTo(reservationDatetime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                () -> assertThat(reservationResponse.getInterviewStartTime())
                        .isEqualTo(reservationDatetime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                () -> assertThat(reservationResponse.getInterviewEndTime())
                        .isEqualTo(reservationDatetime.plusMinutes(INTERVIEW_TIME).toLocalTime()
                                .format(DateTimeFormatter.ofPattern("HH:mm"))
                        ));
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
