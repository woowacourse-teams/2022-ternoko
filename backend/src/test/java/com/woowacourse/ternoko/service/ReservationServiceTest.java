package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static com.woowacourse.ternoko.fixture.ReservationFixture.RESERVATION_REQUEST1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.RESERVATION_REQUEST2;
import static com.woowacourse.ternoko.fixture.ReservationFixture.RESERVATION_REQUEST3;
import static com.woowacourse.ternoko.fixture.ReservationFixture.RESERVATION_REQUEST4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.InvalidReservationDateException;
import com.woowacourse.ternoko.common.exception.ReservationNotFoundException;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    @DisplayName("면담 예약을 생성한다.")
    void create() {
        // given, when
        final Long id = reservationService.create(COACH1.getId(), RESERVATION_REQUEST3);
        final ReservationResponse reservationResponse = reservationService.findReservationById(id);
        final LocalDateTime reservationDatetime = RESERVATION_REQUEST3.getInterviewDatetime();

        // then
        assertAll(
                () -> assertThat(id).isNotNull(),
                () -> assertThat(reservationResponse.getCoachNickname())
                        .isEqualTo(COACH1.getNickname()),
                () -> assertThat(reservationResponse.getInterviewStartTime())
                        .isEqualTo(reservationDatetime),
                () -> assertThat(reservationResponse.getInterviewEndTime())
                        .isEqualTo(reservationDatetime.plusMinutes(INTERVIEW_TIME)),
                () -> assertThat(reservationResponse.getInterviewQuestions())
                        .extracting("question")
                        .contains("고정질문1", "고정질문2", "고정질문3"),
                () -> assertThat(reservationResponse.getInterviewQuestions())
                        .extracting("answer")
                        .contains("답변1", "답변2", "답변3")
        );
    }

    @Test
    @DisplayName("없는 코치로 예약할 시 예외가 발생한다.")
    void create_coachNotFound() {
        assertThatThrownBy(() -> reservationService.create(-1L, RESERVATION_REQUEST3))
                .isInstanceOf(CoachNotFoundException.class);
    }

    @Test
    @DisplayName("없는 면담을 조회할 시 예외가 발생한다.")
    void find_reservationNotFound() {
        assertThatThrownBy(() -> reservationService.findReservationById(-1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    @DisplayName("면담 예약 목록을 조회한다.")
    void findAllReservations() {
        // given
        reservationService.create(COACH1.getId(), RESERVATION_REQUEST1);
        reservationService.create(COACH2.getId(), RESERVATION_REQUEST2);
        reservationService.create(COACH3.getId(), RESERVATION_REQUEST3);
        reservationService.create(COACH4.getId(), RESERVATION_REQUEST4);

        // when
        final List<ReservationResponse> reservationResponses = reservationService.findAllReservations();

        // then
        assertThat(reservationResponses).extracting("crewNickname")
                .hasSize(4)
                .contains("바니", "열음", "앤지", "애쉬");
    }

    @Test
    @DisplayName("코치별로 면담예약 목록을 조회한다.")
    void findAllByCoach() {
        // given
        reservationService.create(COACH4.getId(), RESERVATION_REQUEST1);
        reservationService.create(COACH4.getId(), RESERVATION_REQUEST2);
        reservationService.create(COACH4.getId(), RESERVATION_REQUEST3);
        reservationService.create(COACH4.getId(), RESERVATION_REQUEST4);

        // when
        final ScheduleResponse scheduleResponses = reservationService.findAllByCoach(COACH4.getId(), 2022, 7);

        // then
        assertThat(scheduleResponses.getCalendar()).extracting("crewNickname")
                .hasSize(4)
                .contains("바니", "열음", "앤지", "애쉬");
    }

    @Test
    @DisplayName("면담 예약시, 당일 예약을 시도하면 에러가 발생한다.")
    void createReservationTodayException() {
        final ReservationRequest request = new ReservationRequest("SUDAL", LocalDateTime.now(), FORM_ITEM_REQUESTS);
        assertThatThrownBy(() -> reservationService.create(COACH2.getId(), request))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void createReservationException() {
        final ReservationRequest request = new ReservationRequest("SUDAL", LocalDateTime.now().minusDays(1),
                FORM_ITEM_REQUESTS);
        assertThatThrownBy(() -> reservationService.create(COACH2.getId(), request))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }
}
