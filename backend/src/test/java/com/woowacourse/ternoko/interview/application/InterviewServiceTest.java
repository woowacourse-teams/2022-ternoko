package com.woowacourse.ternoko.interview.application;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_COACH_ID;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_DATE;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_DUPLICATE_DATE_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_3_DAYS;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.PAST_REQUEST;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEM_REQUESTS;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEM_UPDATE_REQUESTS;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.INTERVIEW_TIME;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW3;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeRequest;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.availabledatetime.repository.AvailableDateTimeRepository;
import com.woowacourse.ternoko.dto.CalendarRequest;
import com.woowacourse.ternoko.interview.domain.InterviewStatusType;
import com.woowacourse.ternoko.interview.dto.FormItemDto;
import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import com.woowacourse.ternoko.interview.dto.InterviewResponse;
import com.woowacourse.ternoko.interview.dto.ScheduleResponse;
import com.woowacourse.ternoko.interview.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCoachIdException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCrewIdException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewDateException;
import com.woowacourse.ternoko.service.CoachService;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;

@Sql("/common.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class InterviewServiceTest extends DatabaseSupporter {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private AvailableDateTimeRepository availableDateTimeRepository;

    @Test
    @DisplayName("면담 예약을 생성한다.")
    void create() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        // when
        Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // then
        final InterviewResponse interviewResponse = interviewService.findInterviewResponseById(interviewId);
        assertAll(
                () -> assertThat(interviewResponse.getId()).isNotNull(),
                () -> assertThat(interviewResponse.getCoachNickname())
                        .isEqualTo(COACH3.getNickname()),
                () -> assertThat(interviewResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME)),
                () -> assertThat(interviewResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME).plusMinutes(INTERVIEW_TIME)),
                () -> assertThat(interviewResponse.getInterviewQuestions())
                        .extracting("question")
                        .containsExactly("고정질문1", "고정질문2", "고정질문3"),
                () -> assertThat(interviewResponse.getInterviewQuestions())
                        .extracting("answer")
                        .containsExactly("답변1", "답변2", "답변3")
        );
    }

    @Test
    @DisplayName("면담 예약시, 전날 예약도 가능해야 한다.")
    void createPreviousDay() {
        // given
        LocalDate nextDay = NOW.plusDays(1);
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), new CalendarRequest(
                List.of(new AvailableDateTimeRequest(
                        nextDay.getYear(),
                        nextDay.getMonthValue(),
                        List.of(new AvailableDateTimeSummaryRequest(LocalDateTime.of(nextDay, FIRST_TIME), OPEN))
                ))
        ));

        // when
        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(),
                        LocalDateTime.of(nextDay, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // then
        final InterviewResponse interviewResponse = interviewService.findInterviewResponseById(interviewId);
        assertThat(interviewResponse.getId()).isNotNull();
    }

    @Test
    @DisplayName("면담 예약시 해당 시간이 USED이면 예외가 발생해야 한다.")
    void createUsedDateTimeInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));

        // when & then
        assertThatThrownBy(() -> interviewService.create(CREW2.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_AVAILABLE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("면담 예약 선택 일자가 코치의 가능한 시간이 아닌 경우 예외가 발생한다.")
    void create_WhenInvalidAvailableDateTime() {
        assertThatThrownBy(() -> interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH1.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_AVAILABLE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("면담 예약 선택 일자와 시간이 크루가 다른 코치에게 이미 신청한 면담 시간일 경우 예외가 발생한다.")
    void create_WhenDuplicateReservation() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);

        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));

        // when & then
        assertThatThrownBy(() -> interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_INTERVIEW_DUPLICATE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("없는 면담을 조회할 시 예외가 발생한다.")
    void findInterviewNotFound() {
        assertThatThrownBy(() -> interviewService.findInterviewResponseById(-1L))
                .isInstanceOf(InterviewNotFoundException.class);
    }

    @Test
    @DisplayName("크루 - 정렬된 면담 예약 목록을 조회한다.")
    void findAllInterviews() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH1.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH2.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH1.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH2.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW4.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));

        // when
        final List<InterviewResponse> interviewResponses = interviewService.findAllByCrewId(CREW1.getId());

        // then
        assertThat(interviewResponses.stream()
                .map(InterviewResponse::getCoachNickname))
                .contains(COACH3.getNickname(), COACH2.getNickname(), COACH1.getNickname());
    }

    @Test
    @DisplayName("코치의 면담예약 목록 조회에는 인터뷰의 상태가 포함되어야 한다.")
    void findAllByCoach_contains_interviewStatus() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW2.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW3.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW4.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));

        // when
        final ScheduleResponse scheduleResponses = interviewService.findAllByCoach(COACH4.getId(),
                NOW.getYear(),
                NOW.getMonthValue());

        // then
        assertThat(scheduleResponses.getCalendar()).extracting("interviewStatus")
                .hasSize(4)
                .containsExactly("EDITABLE", "EDITABLE", "EDITABLE", "EDITABLE");
    }

    @Test
    @DisplayName("코치별로 면담예약 목록을 조회한다.")
    void findAllByCoach() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW2.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW3.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW4.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));

        // when
        final ScheduleResponse scheduleResponses = interviewService.findAllByCoach(COACH4.getId(),
                NOW.getYear(),
                NOW.getMonthValue());

        // then
        assertThat(scheduleResponses.getCalendar()).extracting("crewNickname")
                .hasSize(4)
                .contains("수달", "앤지", "애쉬", "록바");
    }

    @Test
    @DisplayName("코치별로 면담예약 목록을 조회할 시, 취소된 면담은 제외하고 보낸다.")
    void findAllByCoach_exclude_interviewStatus_canceled() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);
        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW2.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));
        interviewService.create(CREW3.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        final Long interviewId = interviewService.create(CREW4.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_REQUESTS));

        interviewService.cancelAndDeleteAvailableTime(COACH4.getId(), interviewId, false);

        // when
        final ScheduleResponse scheduleResponses = interviewService.findAllByCoach(COACH4.getId(),
                NOW.getYear(),
                NOW.getMonthValue());

        // then
        assertThat(scheduleResponses.getCalendar()).extracting("crewNickname")
                .hasSize(3)
                .contains("수달", "앤지", "애쉬");
    }

    @Test
    @DisplayName("면담 예약시, 당일 예약을 시도하면 에러가 발생한다.")
    void createInterviewTodayException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);
        // when & then
        assertThatThrownBy(() -> interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now(), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_INTERVIEW_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void createInterviewException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);

        // when & then
        assertThatThrownBy(() -> interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now().minusDays(2), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_INTERVIEW_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약을 수정한다.")
    void update() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        interviewService.update(CREW1.getId(), interviewId, new InterviewRequest(COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                FORM_ITEM_UPDATE_REQUESTS));

        InterviewResponse updatedInterviewResponse = interviewService.findInterviewResponseById(interviewId);
        // then
        assertAll(
                () -> assertThat(updatedInterviewResponse.getId()).isNotNull(),
                () -> assertThat(updatedInterviewResponse.getCoachNickname())
                        .isEqualTo(COACH3.getNickname()),
                () -> assertThat(updatedInterviewResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME)),
                () -> assertThat(updatedInterviewResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME).plusMinutes(INTERVIEW_TIME)),
                () -> assertThat(updatedInterviewResponse.getInterviewQuestions().stream()
                        .map(FormItemDto::getQuestion)
                        .collect(Collectors.toList()))
                        .contains("수정질문1", "수정질문2", "수정질문3"),
                () -> assertThat(updatedInterviewResponse.getInterviewQuestions().stream()
                        .map(FormItemDto::getAnswer)
                        .collect(Collectors.toList()))
                        .contains("수정답변1", "수정답변2", "수정답변3")
        );
    }

    @Test
    @DisplayName("코치가 취소한 면담 예약 수정 시 InterviewStatusType은 CANCELED에서 EDITABLE로 변경되어야한다.")
    void update_WhenCoachCanceledInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));

        interviewService.cancelAndDeleteAvailableTime(COACH3.getId(), interviewId, true);

        // when
        interviewService.update(CREW1.getId(), interviewId,
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS));

        // then
        InterviewResponse updatedInterviewResponse = interviewService.findInterviewResponseById(interviewId);

        assertAll(
                () -> assertThat(updatedInterviewResponse.getId()).isNotNull(),
                () -> assertThat(updatedInterviewResponse.getCoachNickname())
                        .isEqualTo(COACH3.getNickname()),
                () -> assertThat(updatedInterviewResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME)),
                () -> assertThat(updatedInterviewResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME).plusMinutes(INTERVIEW_TIME)),
                () -> assertThat(updatedInterviewResponse.getInterviewQuestions().stream()
                        .map(FormItemDto::getQuestion)
                        .collect(Collectors.toList()))
                        .contains("수정질문1", "수정질문2", "수정질문3"),
                () -> assertThat(updatedInterviewResponse.getInterviewQuestions().stream()
                        .map(FormItemDto::getAnswer)
                        .collect(Collectors.toList()))
                        .contains("수정답변1", "수정답변2", "수정답변3"),
                () -> assertThat(updatedInterviewResponse.getStatus())
                        .isEqualTo(InterviewStatusType.EDITABLE)
        );
    }

    @Test
    @DisplayName("면담 예약을 수정 시 존재하지 않는 예약이라면 예외를 반환한다.")
    void update_WhenInvalidInterviewId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        assertThatThrownBy(() -> interviewService.update(CREW1.getId(), 100L,
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(InterviewNotFoundException.class)
                .hasMessage(100L + INTERVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("면담 예약을 수정 시 크루 본인의 예약이 아니라면 예외를 반환한다.")
    void update_WhenInvalidCrewId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        assertThatThrownBy(() -> interviewService.update(CREW2.getId(), interviewId,
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(InvalidInterviewCrewIdException.class)
                .hasMessage(INVALID_INTERVIEW_CREW_ID.getMessage());
    }

    @Test
    @DisplayName("면담 예약 수정 시 USED 라면 예외를 반환한다.")
    void update_WhenInvalidAvailableDateTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        assertThatThrownBy(() -> interviewService.update(CREW1.getId(), interviewId,
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_AVAILABLE_DATE_TIME.getMessage());
    }

    @Test
    @DisplayName("면담 수정 시, 당일 예약을 시도하면 에러가 발생한다.")
    void updateInterviewTodayException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);
        assertThatThrownBy(() -> interviewService.update(CREW1.getId(), interviewId,
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now(), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_INTERVIEW_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 수정 시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void updateInterviewException() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when & then
        coachService.putAvailableDateTimesByCoachId(COACH4.getId(), PAST_REQUEST);
        assertThatThrownBy(() -> interviewService.update(CREW1.getId(), interviewId,
                new InterviewRequest(COACH4.getId(), LocalDateTime.of(LocalDate.now().minusDays(2), THIRD_TIME),
                        FORM_ITEM_REQUESTS)))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_INTERVIEW_DATE.getMessage());
    }

    @Test
    @DisplayName("크루가 면담 예약을 삭제한다.")
    void delete() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        interviewService.delete(CREW1.getId(), interviewId);

        // then
        assertThatThrownBy(() -> interviewService.findInterviewResponseById(interviewId))
                .isInstanceOf(InterviewNotFoundException.class);
    }

    @Test
    @DisplayName("크루 - 코치가 취소한 면담 예약을 삭제한다.")
    void deleteCanceledInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        interviewService.cancelAndDeleteAvailableTime(COACH3.getId(), interviewId, true);
        interviewService.delete(CREW1.getId(), interviewId);

        // then
        assertThatThrownBy(() -> interviewService.findInterviewResponseById(interviewId))
                .isInstanceOf(InterviewNotFoundException.class);
    }

    @Test
    @DisplayName("크루 - 코치가 취소한 (되는시간까지 삭제한) 면담 예약을 수정한다.")
    void updateCanceledInterviewWithEmptyTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        interviewService.cancelAndDeleteAvailableTime(COACH3.getId(), interviewId, false);

        // then
        assertDoesNotThrow(
                () -> interviewService.update(CREW1.getId(), interviewId, new InterviewRequest(COACH3.getId(),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        FORM_ITEM_UPDATE_REQUESTS))
        );
    }

    @Test
    @DisplayName("크루 - 코치가 취소한 (되는시간까지 삭제한) 면담 예약을 삭제한다.")
    void deleteCanceledInterviewWithEmptyTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        interviewService.cancelAndDeleteAvailableTime(COACH3.getId(), interviewId, false);
        interviewService.delete(CREW1.getId(), interviewId);

        // then
        assertThatThrownBy(() -> interviewService.findInterviewResponseById(interviewId))
                .isInstanceOf(InterviewNotFoundException.class);
    }

    @Test
    @DisplayName("코치가 시간이 안돼서 면담 예약을 취소할 때, 되는 시간도 지워준다.")
    void cancelWithDeleteAvailableDateTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        interviewService.cancelAndDeleteAvailableTime(COACH3.getId(), interviewId, true);
        final InterviewResponse canceledResponse = interviewService.findInterviewResponseById(interviewId);
        final boolean expected = availableDateTimeRepository.findByCoachIdAndInterviewDateTime(COACH3.getId(),
                canceledResponse.getInterviewEndTime()).isEmpty();
        // then

        assertAll(
                () -> assertThat(canceledResponse.getStatus()).isEqualTo(InterviewStatusType.CANCELED),
                () -> assertTrue(expected)
        );
    }

    @Test
    @DisplayName("코치가 개인 사정이 생겨서 면담 예약을 취소할 때, 되는 시간은 유지된다.")
    void only_cancel() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH2.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH2.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME),
                        FORM_ITEM_REQUESTS));
        // when
        interviewService.cancelAndDeleteAvailableTime(COACH2.getId(), interviewId, true);

        final InterviewResponse canceledInterview = interviewService.findInterviewResponseById(interviewId);

        final Optional<AvailableDateTime> dateTime = availableDateTimeRepository.findByCoachIdAndInterviewDateTime(
                COACH2.getId(),
                canceledInterview.getInterviewEndTime());
        // then

        assertAll(
                () -> assertThat(canceledInterview.getStatus()).isEqualTo(InterviewStatusType.CANCELED),
                () -> assertFalse(dateTime.isPresent())
        );
    }

    @Test
    @DisplayName("코치가 면담 예약을 취소 시 본인의 면담 예약이 아닌 경우 예외를 반환한다.")
    void cancel_WhenInvalidCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        final Long interviewId = interviewService.create(CREW1.getId(),
                new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        FORM_ITEM_REQUESTS));
        // when  && then
        assertThatThrownBy(
                () -> interviewService.cancelAndDeleteAvailableTime(COACH2.getId(), interviewId, true))
                .isInstanceOf(InvalidInterviewCoachIdException.class)
                .hasMessage(INVALID_INTERVIEW_COACH_ID.getMessage());
    }

    @Test
    @Disabled
    @DisplayName("같은 가능 시간에 여러개의 면담을 생성할 수 없다. (동시성테스트)")
    void test_con() {
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);
        ExecutorService service = Executors.newCachedThreadPool();

        for (Long i = 5L; i <= 6L; i++) {
            final Long finalI = i;
            service.execute(() -> {
                try {
                    interviewService.create(finalI,
                            new InterviewRequest(COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME),
                                    FORM_ITEM_REQUESTS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
