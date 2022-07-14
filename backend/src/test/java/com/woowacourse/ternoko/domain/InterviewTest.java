package com.woowacourse.ternoko.domain;

import static com.woowacourse.ternoko.domain.Interview.INVALID_LOCAL_DATE_TIME_EXCEPTION_MESSAGE;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEMS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InterviewTest {

    @Test
    @DisplayName("면담을 예약한다.")
    void createReservation() {
        final LocalDateTime reservationTime = LocalDateTime.now().plusDays(2);
        final LocalDateTime reservationEndTime = reservationTime.plusMinutes(30);
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Interview(reservationTime, reservationEndTime, COACH1, "열음", FORM_ITEMS));
    }

    @Test
    @DisplayName("면담 예약시, 당일 예약을 시도하면 에러가 발생한다.")
    void createReservationTodayException() {
        final LocalDateTime nowTime = LocalDateTime.now();
        final LocalDateTime reservationEndTime = nowTime.plusMinutes(30);
        assertThatThrownBy(() -> new Interview(nowTime, reservationEndTime, COACH1, "열음", FORM_ITEMS)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(INVALID_LOCAL_DATE_TIME_EXCEPTION_MESSAGE);
    }

    @Test
    @DisplayName("면담 예약시,과거 기간 예약을 시도하면 에러가 발생한다.")
    void createReservationException() {
        final LocalDateTime nowTime = LocalDateTime.now();
        final LocalDateTime pastTime = nowTime.minusDays(1);
        final LocalDateTime reservationEndTime = nowTime.plusMinutes(30);
        assertThatThrownBy(() -> new Interview(pastTime, reservationEndTime, COACH1, "열음", FORM_ITEMS)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(INVALID_LOCAL_DATE_TIME_EXCEPTION_MESSAGE);
    }
}
