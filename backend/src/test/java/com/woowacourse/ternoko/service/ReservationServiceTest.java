package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.domain.Location;
import com.woowacourse.ternoko.dto.FormItemRequest;
import com.woowacourse.ternoko.dto.ReservationRequest;
import com.woowacourse.ternoko.dto.ReservationResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

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

        // when
        final Long id = reservationService.create(COACH1.getId(), reservationRequest);
        final ReservationResponse reservationResponse = reservationService.findReservationById(id);
        final LocalDateTime reservationDatetime = reservationRequest.getReservationDatetime();

        // then
        assertAll(
                () -> assertThat(id).isNotNull(),
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
