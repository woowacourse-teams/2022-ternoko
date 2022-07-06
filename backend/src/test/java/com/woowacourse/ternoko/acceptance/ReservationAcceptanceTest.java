package com.woowacourse.ternoko.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.domain.Location;
import com.woowacourse.ternoko.dto.FormItemRequest;
import com.woowacourse.ternoko.dto.ReservationRequest;
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
}
