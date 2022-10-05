package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DATE;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.DELETED;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture._2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_브리_2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_준_2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_준_2022_07_01_11_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_준_2022_07_01_12_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_토미_2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_토미_2022_07_01_11_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_토미_2022_07_01_12_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간생성요청정보_2022_07_01_10_TO_12;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간생성요청정보_2022_07_02_10_TO_12;
import static com.woowacourse.ternoko.support.fixture.refactor.InterviewFixture.면담사전질문요청정보들;
import static com.woowacourse.ternoko.support.fixture.refactor.InterviewFixture.면담생성요청정보_준_2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.InterviewFixture.면담생성요청정보_토미_2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.김애쉬;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.네오;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.브리;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.손앤지;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.준;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.토미;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.허수달;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.ternoko.common.exception.AvailableDateTimeNotFoundException;
import com.woowacourse.ternoko.common.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewDateException;
import com.woowacourse.ternoko.core.application.CoachService;
import com.woowacourse.ternoko.core.application.InterviewService;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.dto.request.FormItemRequest;
import com.woowacourse.ternoko.core.dto.request.InterviewRequest;
import com.woowacourse.ternoko.core.dto.response.FormItemResponse;
import com.woowacourse.ternoko.core.dto.response.InterviewResponse;
import com.woowacourse.ternoko.core.dto.response.ScheduleResponse;
import com.woowacourse.ternoko.support.alarm.AlarmResponseCache;
import com.woowacourse.ternoko.support.alarm.SlackAlarm;
import com.woowacourse.ternoko.support.time.TimeMachine;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class InterviewServiceTest extends DatabaseSupporter {

    @MockBean
    private SlackAlarm slackAlarm;

    @MockBean
    private AlarmResponseCache cache;

    @Autowired
    private AvailableDateTimeRepository availableDateTimeRepository;

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private CoachService coachService;

    @BeforeEach
    void setUp() {
        doNothing().when(slackAlarm).sendCreateMessage(any());
        doNothing().when(slackAlarm).sendCancelMessage(any());
        doNothing().when(slackAlarm).sendUpdateMessage(any(),any());
        doNothing().when(slackAlarm).sendDeleteMessage(any());

        현재시간_설정(2022, 6, 20, 10, 0);
    }

    @AfterEach
    void reset() {
        TimeMachine.reset();
    }

    @Test
    @DisplayName("크루 - 면담 예약을 생성한다.")
    void create() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);

        // then
        final InterviewResponse interviewResponse = interviewService.findInterviewResponseById(interviewId);
        assertAll(
                () -> assertThat(interviewResponse.getId()).isNotNull(),
                () -> assertThat(interviewResponse.getCoachNickname())
                        .isEqualTo(COACH1.getNickname()),
                () -> assertThat(interviewResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(2022, 7, 1, 10, 0)),
                () -> assertThat(interviewResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(2022, 7, 1, 10, 30)),
                () -> assertThat(interviewResponse.getInterviewQuestions())
                        .extracting("question")
                        .containsExactly("고정질문1", "고정질문2", "고정질문3"),
                () -> assertThat(interviewResponse.getInterviewQuestions())
                        .extracting("answer")
                        .containsExactly("답변1", "답변2", "답변3")
        );
    }

    @Test
    @DisplayName("크루 - 면담 예약시, 전날 예약도 가능해야 한다.")
    void createPreviousDay() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        현재시간_설정(2022, 6, 20, 10, 0);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);

        // then
        final InterviewResponse interviewResponse = interviewService.findInterviewResponseById(interviewId);
        assertThat(interviewResponse.getId()).isNotNull();
    }

    @Test
    @DisplayName("크루 - 같은 시간에 취소된 면담이 있어도 면담을 신청할 수 있다.")
    void createInterviewExistCanceledInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.cancelAndDeleteAvailableTime(interviewId, true);

        // then
        final AvailableDateTime availableDateTime = new AvailableDateTime(4L, 준.getId(), _2022_07_01_10_00, OPEN);
        assertDoesNotThrow(() -> interviewService.create(허수달.getId(), 면담생성요청정보(준, availableDateTime)));
    }

    @Test
    @DisplayName("크루 - 면담 예약시 해당 시간이 다른곳에서 사용중인 시간이면 예외가 발생해야 한다.")
    void createUsedDateTimeInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);

        // then
        assertThatThrownBy(() -> interviewService.create(김애쉬.getId(), 면담생성요청정보_준_2022_07_01_10_00))
                .isInstanceOf(InvalidInterviewDateException.class);
    }

    @Test
    @DisplayName("크루 - 면담 예약 선택 일자가 코치의 가능한 시간이 아닌 경우 예외가 발생한다.")
    void create_WhenInvalidAvailableDateTime() {
        assertThatThrownBy(() -> interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00))
                .isInstanceOf(AvailableDateTimeNotFoundException.class);
    }

    @Test
    @DisplayName("크루 - 면담 예약 선택 일자와 시간이 크루가 다른 코치에게 이미 신청한 면담 시간일 경우 예외가 발생한다.")
    void create_WhenDuplicateReservation() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(토미.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);

        // then
        assertThatThrownBy(() -> interviewService.create(허수달.getId(), 면담생성요청정보_토미_2022_07_01_10_00))
                .isInstanceOf(InvalidInterviewDateException.class);
    }

    @Test
    @DisplayName("크루 - 없는 면담을 조회할 시 예외가 발생한다.")
    void findInterviewNotFound() {
        assertThatThrownBy(() -> interviewService.findInterviewResponseById(-1L))
                .isInstanceOf(InterviewNotFoundException.class);
    }

    @Test
    @DisplayName("크루 - 정렬된 면담 예약 목록을 조회한다.")
    void findAllInterviews() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_02_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(토미.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(브리.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(네오.getId(), 면담가능시간생성요청정보_2022_07_02_10_TO_12);

        final List<AvailableDateTime> 준_면담가능시간_2022_07 = coachService.findAvailableDateTimesByCoachId(
                준.getId(), 2022, 7);
        final AvailableDateTime 면담가능시간_준_2022_07_02_10_00 = 준_면담가능시간_2022_07.get(0);
        final List<AvailableDateTime> 네오_면담가능시간_2022_07 = coachService.findAvailableDateTimesByCoachId(
                네오.getId(), 2022, 7);
        final AvailableDateTime 면담가능시간_네오_2022_07_02_11_00 = 네오_면담가능시간_2022_07.get(1);

        interviewService.create(허수달.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_02_10_00));
        interviewService.create(허수달.getId(), 면담생성요청정보(토미, 면담가능시간_토미_2022_07_01_11_00));
        interviewService.create(허수달.getId(), 면담생성요청정보(브리, 면담가능시간_브리_2022_07_01_10_00));
        interviewService.create(허수달.getId(), 면담생성요청정보(네오, 면담가능시간_네오_2022_07_02_11_00));

        // when
        final List<InterviewResponse> interviewResponses = interviewService.findAllByCrewId(허수달.getId());

        // then
        assertThat(interviewResponses.stream()
                .map(InterviewResponse::getCoachNickname))
                .contains(브리.getNickname(), 토미.getNickname(), 준.getNickname());
    }

    private InterviewRequest 면담생성요청정보(final Coach coach, final AvailableDateTime availableDateTime) {
        return new InterviewRequest(coach.getId(),
                availableDateTime.getId(),
                availableDateTime.getLocalDateTime(),
                면담사전질문요청정보들);
    }

    @Test
    @DisplayName("코치의 면담예약 목록 조회에는 인터뷰의 상태가 포함되어야 한다.")
    void findAllByCoach_contains_interviewStatus() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        interviewService.create(허수달.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_10_00));
        interviewService.create(허수달.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_11_00));
        interviewService.create(허수달.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_12_00));

        // when
        final ScheduleResponse scheduleResponse = interviewService.findAllByCoach(준.getId(), 2022, 7);

        // then
        assertThat(scheduleResponse.getCalendar()).extracting("interviewStatus")
                .hasSize(3)
                .containsExactly("EDITABLE", "EDITABLE", "EDITABLE");
    }

    @Test
    @DisplayName("코치 - 면담예약 목록을 조회한다.")
    void findAllByCoach() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        interviewService.create(허수달.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_10_00));
        interviewService.create(김애쉬.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_11_00));
        interviewService.create(손앤지.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_12_00));
        final ScheduleResponse scheduleResponses = interviewService.findAllByCoach(준.getId(), 2022, 7);

        // then
        assertAll(() -> assertThat(scheduleResponses.getCalendar())
                .hasSize(3));
    }

    @Test
    @DisplayName("코치 - 면담예약 목록을 조회할 시, 취소된 면담은 제외하고 보낸다.")
    void findAllByCoach_exclude_interviewStatus_canceled() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_10_00));
        interviewService.create(김애쉬.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_11_00));
        interviewService.create(손앤지.getId(), 면담생성요청정보(준, 면담가능시간_준_2022_07_01_12_00));
        interviewService.cancelAndDeleteAvailableTime(interviewId, false);
        final ScheduleResponse scheduleResponses = interviewService.findAllByCoach(준.getId(), 2022, 7);

        // then
        assertThat(scheduleResponses.getCalendar()).extracting("crewNickname")
                .hasSize(2)
                .contains("앤지", "애쉬");
    }

    @Test
    @DisplayName("면담 예약시, 당일 예약을 시도하면 에러가 발생한다.")
    void createInterviewTodayException() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        현재시간_설정(2022, 7, 1, 9, 0);

        // when & then
        assertThatThrownBy(() -> interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_INTERVIEW_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void createInterviewException() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        현재시간_설정(2022, 7, 2, 9, 0);

        // when & then
        assertThatThrownBy(() -> interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00))
                .isInstanceOf(InvalidInterviewDateException.class)
                .hasMessage(INVALID_INTERVIEW_DATE.getMessage());
    }

    @Test
    @DisplayName("면담 예약을 수정한다.")
    void update() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(토미.getId(), 면담가능시간생성요청정보_2022_07_02_10_TO_12);
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);

        // when
        interviewService.update(허수달.getId(), interviewId, 토미면담요청정보생성_2022_07_02_10_00());
        final InterviewResponse updatedInterviewResponse = interviewService.findInterviewResponseById(interviewId);

        // then
        assertAll(
                () -> assertThat(updatedInterviewResponse.getId()).isNotNull(),
                () -> assertThat(updatedInterviewResponse.getCoachNickname())
                        .isEqualTo("토미"),
                () -> assertThat(updatedInterviewResponse.getInterviewStartTime())
                        .isEqualTo(LocalDateTime.of(2022, 7, 2, 10, 0)),
                () -> assertThat(updatedInterviewResponse.getInterviewEndTime())
                        .isEqualTo(LocalDateTime.of(2022, 7, 2, 10, 30)),
                () -> assertThat(updatedInterviewResponse.getInterviewQuestions().stream()
                            .map(FormItemResponse::getQuestion)
                            .collect(Collectors.toList()))
                        .contains("수정질문", "고정질문2", "고정질문3"),
                () -> assertThat(updatedInterviewResponse.getInterviewQuestions().stream()
                            .map(FormItemResponse::getAnswer)
                            .collect(Collectors.toList()))
                        .contains("수정답변", "답변2", "답변3")
        );
    }

    @Test
    @DisplayName("면담 예약 수정 시 존재하지 않는 예약이라면 예외를 반환한다.")
    void update_WhenInvalidInterviewId() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(토미.getId(), 면담가능시간생성요청정보_2022_07_02_10_TO_12);

        // when
        interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);

        // when
        assertThatThrownBy(() -> interviewService.update(허수달.getId(), -1L, 토미면담요청정보생성_2022_07_02_10_00()))
                .isInstanceOf(InterviewNotFoundException.class);
    }

    @Test
    @DisplayName("면담 수정 시, 당일 수정을 시도하면 에러가 발생한다.")
    void updateInterviewTodayException() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(토미.getId(), 면담가능시간생성요청정보_2022_07_02_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        현재시간_설정(2022, 7, 2, 9, 0);

        // then
        assertThatThrownBy(() -> interviewService.update(허수달.getId(), interviewId, 면담생성요청정보(토미, 면담가능시간_토미_2022_07_01_10_00)))
                .isInstanceOf(InvalidInterviewDateException.class);
    }

    @Test
    @DisplayName("면담 수정 시, 과거 기간 예약을 시도하면 에러가 발생한다.")
    void updateInterviewException() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        현재시간_설정(2022, 7, 2, 9, 0);

        // then
        assertThatThrownBy(() -> interviewService.update(허수달.getId(), interviewId, 면담생성요청정보(준, 면담가능시간_준_2022_07_01_12_00)))
                .isInstanceOf(InvalidInterviewDateException.class);
    }

    @Test
    @DisplayName("면담 예약 수정 시 예약할 시간에 자신의 다른 면담이 있으면 수정이 불가능하다.")
    void update_WhenDuplicateReservation() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        coachService.putAvailableDateTimesByCoachId(토미.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.create(허수달.getId(), 면담생성요청정보(토미, 면담가능시간_토미_2022_07_01_12_00));

        // then
        assertThatThrownBy(() -> interviewService.update(허수달.getId(), interviewId, 면담생성요청정보(준, 면담가능시간_준_2022_07_01_12_00)))
                .isInstanceOf(InvalidInterviewDateException.class);
    }

    @Test
    @DisplayName("크루 - 면담 예약을 삭제한다.")
    void delete() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.delete(허수달.getId(), interviewId);

        // then
        assertThatThrownBy(() -> interviewService.findInterviewResponseById(interviewId))
                .isInstanceOf(InterviewNotFoundException.class);
    }

    @Test
    @DisplayName("크루 - 코치가 취소한 (되는시간은 살린) 면담 예약을 수정한다.")
    void updateCanceledInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.cancelAndDeleteAvailableTime(interviewId, true);

        // then
        assertDoesNotThrow(() -> interviewService.update(허수달.getId(), interviewId, 면담생성요청정보(준, 면담가능시간_준_2022_07_01_12_00)));
    }

    @Test
    @DisplayName("크루 - 코치가 취소한 (되는시간은 살린) 면담 예약을 삭제한다.")
    void deleteCanceledInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.cancelAndDeleteAvailableTime(interviewId, true);

        // then
        assertDoesNotThrow(() -> interviewService.delete(허수달.getId(), interviewId));
    }

    @Test
    @DisplayName("크루 - 코치가 취소한 (되는시간까지 삭제한) 면담 예약을 수정한다.")
    void updateCanceledInterviewWithEmptyTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.cancelAndDeleteAvailableTime(interviewId, false);

        // then
        assertDoesNotThrow(() -> interviewService.update(허수달.getId(), interviewId, 면담생성요청정보(준, 면담가능시간_준_2022_07_01_12_00)));
    }

    @Test
    @DisplayName("크루 - 코치가 취소한 (되는시간까지 삭제한) 면담 예약을 삭제한다.")
    void deleteCanceledInterviewWithEmptyTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.cancelAndDeleteAvailableTime(interviewId, false);

        // then
        assertDoesNotThrow(() -> interviewService.delete(허수달.getId(), interviewId));
    }

    @Test
    @DisplayName("코치 - 되는시간까지 삭제하며 면담 예약을 취소할 때, 되는 시간도 지워준다.")
    void cancelWithDeleteAvailableDateTime() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.cancelAndDeleteAvailableTime(interviewId, false);

        // then
        assertThat(availableDateTimeRepository.findById(면담가능시간_준_2022_07_01_10_00.getId()).get()
                .getAvailableDateTimeStatus())
                .isEqualTo(DELETED);
    }

    @Test
    @DisplayName("코치 - 되는시간은 유지하며 면담 예약을 취소할 때, 되는 시간은 유지된다.")
    void cancelOnlyInterview() {
        // given
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);

        // when
        final Long interviewId = interviewService.create(허수달.getId(), 면담생성요청정보_준_2022_07_01_10_00);
        interviewService.cancelAndDeleteAvailableTime(interviewId, true);

        // then
        assertThat(availableDateTimeRepository.findById(면담가능시간_준_2022_07_01_10_00.getId()).isPresent()).isTrue();
    }

    @Test
    @Disabled
    @DisplayName("같은 가능 시간에 여러개의 면담을 생성할 수 없다. (동시성테스트)")
    void test_concurrent() {
        coachService.putAvailableDateTimesByCoachId(준.getId(), 면담가능시간생성요청정보_2022_07_01_10_TO_12);
        ExecutorService service = Executors.newCachedThreadPool();

        for (Long i = 5L; i <= 6L; i++) {
            final Long finalI = i;
            service.execute(() -> {
                try {
                    interviewService.create(finalI, 면담생성요청정보_준_2022_07_01_10_00);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void 현재시간_설정(final int year, final int month, final int day, final int hour, final int minute) {
        TimeMachine.timeTravelAt(LocalDateTime.of(year, month, day, hour, minute));
    }

    private InterviewRequest 토미면담요청정보생성_2022_07_02_10_00() {
        final List<AvailableDateTime> 토미_면담가능시간_2022_07 = coachService.findAvailableDateTimesByCoachId(
                토미.getId(), 2022, 7);
        final List<FormItemRequest> formItemRequests = List.of(new FormItemRequest("수정질문", "수정답변"));
        return new InterviewRequest(토미.getId(),
                토미_면담가능시간_2022_07.get(0).getId(),
                토미_면담가능시간_2022_07.get(0).getLocalDateTime(),
                formItemRequests);
    }
}
