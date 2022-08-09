package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_RESERVATION_COACH_ID;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_3_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.PAST_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW3;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_UPDATE_REQUESTS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.InvalidReservationCoachIdException;
import com.woowacourse.ternoko.common.exception.InvalidReservationCrewIdException;
import com.woowacourse.ternoko.common.exception.InvalidReservationDateException;
import com.woowacourse.ternoko.common.exception.ReservationNotFoundException;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.InterviewStatusType;
import com.woowacourse.ternoko.domain.Reservation;
import com.woowacourse.ternoko.dto.InterviewResponse;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import java.time.LocalDate;
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

    @Autowired
    private CoachService coachService;

    @Test
    @DisplayName("면담 예약을 생성한다.")
    void create() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        // when
        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // then
        final ReservationResponse reservationResponse = reservationService.findReservationById(reservation.getId());
        assertAll(
                () -> assertThat(reservation.getId()).isNotNull(),
                () -> assertThat(reservationResponse.getCoachNickname())
                        .isEqualTo(COACH3.getNickname()),
                () -> assertThat(reservationResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME)),
                () -> assertThat(reservationResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME).plusMinutes(INTERVIEW_TIME)),
                () -> assertThat(reservationResponse.getInterviewQuestions())
                        .extracting("question")
                        .contains("고정질문1", "고정질문2", "고정질문3"),
                () -> assertThat(reservationResponse.getInterviewQuestions())
                        .extracting("answer")
                        .contains("답변1", "답변2", "답변3")
        );
    }

    @Test
    @DisplayName("면담 예약 선택 일자가 코치의 가능한 시간이 아닌 경우 예외가 발생한다.")
    void create_WhenInvalidAvailableDateTime() {
        assertThatThrownBy(() -> reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH1.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_AVAILABLE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("없는 코치로 예약할 시 예외가 발생한다.")
    void create_coachNotFound() {
        assertThatThrownBy(() -> reservationService.create(CREW1.getId(),
                new ReservationRequest(-1L, LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME), FORM_ITEM_REQUESTS)))
                .isInstanceOf(CoachNotFoundException.class);
    }

    @Test
    @DisplayName("면담 예약 선택 일자와 시간이 크루가 이미 신청한 면담 시간일 경우 예외가 발생한다.")
    void create_WhenDuplicateReservation() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);
        reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));

        // when & then
        assertThatThrownBy(() -> reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DUPLICATE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("없는 면담을 조회할 시 예외가 발생한다.")
    void find_reservationNotFound() {
        assertThatThrownBy(() -> reservationService.findReservationById(-1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    @DisplayName("크루 - 정렬된 면담 예약 목록을 조회한다.")
    void findAllReservations() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH1.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH2.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH1.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH2.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));
        reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        reservationService.create(CREW4.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));

        // when
        final List<InterviewResponse> interviewResponses = reservationService.findAllByCrewId(CREW1.getId());

        // then
        assertThat(interviewResponses.stream()
                .map(InterviewResponse::getCoachNickname))
                .containsExactly(COACH3.getNickname(), COACH2.getNickname(), COACH1.getNickname());
    }

    @Test
    @DisplayName("코치별로 면담예약 목록을 조회한다.")
    void findAllByCoach() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        reservationService.create(CREW2.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));
        reservationService.create(CREW3.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        reservationService.create(CREW4.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));

        // when
        final ScheduleResponse scheduleResponses = reservationService.findAllByCoach(COACH4.getId(),
                NOW.getYear(),
                NOW.getMonthValue());

        // then
        assertThat(scheduleResponses.getCalendar()).extracting("crewNickname")
                .hasSize(4)
                .contains("수달", "앤지", "애쉬", "록바");
    }

    @Test
    @DisplayName("면담 예약시, 당일 예약을 시도하면 에러가 발생한다.")
    void createReservationTodayException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);
        // when & then
        assertThatThrownBy(() -> reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now(), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void createReservationException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);

        // when & then
        assertThatThrownBy(() -> reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now().minusDays(2), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약을 수정한다.")
    void update() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        final Reservation updateReservation = reservationService.update(CREW1.getId(), reservation.getId(),
                new ReservationRequest(COACH3.getId(),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS));

        // then
        assertAll(
                () -> assertThat(updateReservation.getId()).isNotNull(),
                () -> assertThat(updateReservation.getInterview().getCoach().getNickname())
                        .isEqualTo(COACH3.getNickname()),
                () -> assertThat(updateReservation.getInterview().getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME)),
                () -> assertThat(updateReservation.getInterview().getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME).plusMinutes(INTERVIEW_TIME)),
                () -> assertThat(updateReservation.getInterview().getFormItems())
                        .extracting("question")
                        .contains("수정질문1", "수정질문2", "수정질문3"),
                () -> assertThat(updateReservation.getInterview().getFormItems())
                        .extracting("answer")
                        .contains("수정답변1", "수정답변2", "수정답변3")
        );
    }

    @Test
    @DisplayName("면담 예약을 수정 시 존재하지 않는 예약이라면 예외를 반환한다.")
    void update_WhenInvalidReservationId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        assertThatThrownBy(() -> reservationService.update(CREW2.getId(), 2L,
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage(2L + ExceptionType.RESERVATION_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("면담 예약을 수정 시 크루 본인의 예약이 아니라면 예외를 반환한다.")
    void update_WhenInvalidCrewId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        assertThatThrownBy(() -> reservationService.update(CREW2.getId(), reservation.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(InvalidReservationCrewIdException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_CREW_ID.getMessage());
    }

    @Test
    @DisplayName("면담 예약을 수정 시 선택한 코치가 존재하지 않는다면 예외를 반환한다.")
    void update_WhenCoachNotFound() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        assertThatThrownBy(() -> reservationService.update(CREW1.getId(), reservation.getId(),
                new ReservationRequest(5L, LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(CoachNotFoundException.class)
                .hasMessage(5L + ExceptionType.COACH_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("면담 예약 수정 시 코치의 가능 시간이 아니라면 예외를 반환한다.")
    void update_WhenInvalidAvailableDateTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        assertThatThrownBy(() -> reservationService.update(CREW1.getId(), reservation.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_AVAILABLE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("면담 예약 수정 시 면담 예약 선택 일자와 시간이 크루가 이미 신청한 면담 시간일 경우 예외가 발생한다.")
    void update_WhenDuplicateReservation() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));

        // when & then
        assertThatThrownBy(() -> reservationService.update(CREW1.getId(), reservation.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DUPLICATE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("면담 수정 시, 당일 예약을 시도하면 에러가 발생한다.")
    void updateReservationTodayException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);
        assertThatThrownBy(() -> reservationService.update(CREW1.getId(), reservation.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now(), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 수정 시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void updateReservationException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);
        assertThatThrownBy(() -> reservationService.update(CREW1.getId(), reservation.getId(),
                new ReservationRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now().minusDays(2), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약을 삭제한다.")
    void delete() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        Reservation updateReservation = reservationService.delete(CREW1.getId(), reservation.getId());

        // then
        assertThatThrownBy(() -> reservationService.findReservationById(updateReservation.getId()))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    @DisplayName("코치가 면담 예약을 취소한다.")
    void cancel() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        Interview canceledInterview = reservationService.cancel(COACH3.getId(), reservation.getId());

        // then
        assertThat(canceledInterview.getInterviewStatusType()).isEqualTo(InterviewStatusType.CANCELED);
    }

    @Test
    @DisplayName("코치가 면담 예약을 취소 시 본인의 면담 예약이 아닌 경우 예외를 반환한다.")
    void cancel_WhenInvalidCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Reservation reservation = reservationService.create(CREW1.getId(),
                new ReservationRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when  && then
        assertThatThrownBy(() -> reservationService.cancel(COACH2.getId(), reservation.getId()))
                .isInstanceOf(InvalidReservationCoachIdException.class)
                .hasMessage(INVALID_RESERVATION_COACH_ID.getMessage());
    }
}
