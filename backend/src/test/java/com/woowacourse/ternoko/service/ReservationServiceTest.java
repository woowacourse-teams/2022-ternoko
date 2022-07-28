package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_3_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.PAST_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;
import static com.woowacourse.ternoko.fixture.ReservationFixture.INTERVIEW_TIME;
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
        // given, when
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long id = reservationService.create(COACH3.getId(), new ReservationRequest("앤지",
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                FORM_ITEM_REQUESTS));

        final ReservationResponse reservationResponse = reservationService.findReservationById(id);

        // then
        assertAll(
                () -> assertThat(id).isNotNull(),
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
        assertThatThrownBy(() -> reservationService.create(COACH1.getId(), new ReservationRequest("앤지",
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_AVAILABLE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("없는 코치로 예약할 시 예외가 발생한다.")
    void create_coachNotFound() {
        assertThatThrownBy(() -> reservationService.create(-1L, new ReservationRequest("앤지",
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                FORM_ITEM_REQUESTS)))
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
        coachService.putAvailableDateTimesByCoachId(COACH1.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH2.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        reservationService.create(COACH1.getId(),
                new ReservationRequest("바니", LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME), FORM_ITEM_REQUESTS));
        reservationService.create(COACH2.getId(),
                new ReservationRequest("열음", LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME), FORM_ITEM_REQUESTS));
        reservationService.create(COACH3.getId(),
                new ReservationRequest("앤지", LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME), FORM_ITEM_REQUESTS));
        reservationService.create(COACH4.getId(),
                new ReservationRequest("애쉬", LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME), FORM_ITEM_REQUESTS));

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
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        reservationService.create(COACH4.getId(),
                new ReservationRequest("바니", LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME), FORM_ITEM_REQUESTS));
        reservationService.create(COACH4.getId(),
                new ReservationRequest("열음", LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME), FORM_ITEM_REQUESTS));
        reservationService.create(COACH4.getId(),
                new ReservationRequest("앤지", LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME), FORM_ITEM_REQUESTS));
        reservationService.create(COACH4.getId(),
                new ReservationRequest("애쉬", LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME), FORM_ITEM_REQUESTS));

        // when
        final ScheduleResponse scheduleResponses = reservationService.findAllByCoach(COACH4.getId(),
                NOW.getYear(),
                NOW.getMonthValue());

        // then
        assertThat(scheduleResponses.getCalendar()).extracting("crewNickname")
                .hasSize(4)
                .contains("바니", "열음", "앤지", "애쉬");
    }

    @Test
    @DisplayName("면담 예약시, 당일 예약을 시도하면 에러가 발생한다.")
    void createReservationTodayException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);

        // when & then
        assertThatThrownBy(() -> reservationService.create(COACH4.getId(),
                new ReservationRequest("SUDAL", LocalDateTime.of(LocalDate.now(), THIRD_TIME), FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void createReservationException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);

        // when & then
        assertThatThrownBy(() -> reservationService.create(COACH4.getId(),
                new ReservationRequest("SUDAL", LocalDateTime.of(LocalDate.now().minusDays(2), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidReservationDateException.class)
                .hasMessage(ExceptionType.INVALID_RESERVATION_DATE.getMessage());
    }
}
