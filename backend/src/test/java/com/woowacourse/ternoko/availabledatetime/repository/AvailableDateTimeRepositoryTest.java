package com.woowacourse.ternoko.availabledatetime.repository;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import com.woowacourse.ternoko.interview.domain.formitem.Answer;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import com.woowacourse.ternoko.interview.domain.formitem.Question;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AvailableDateTimeRepositoryTest {

    @Autowired
    private AvailableDateTimeRepository availableDateTimeRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Coach coach;
    private Crew crew;

    @BeforeEach
    void setUp() {
        coach = memberRepository.save(COACH1);
        crew = memberRepository.save(CREW1);
    }

    @AfterEach
    void cleanUp() {
        availableDateTimeRepository.deleteAll();
        interviewRepository.deleteAll();
        memberRepository.delete(COACH1);
        memberRepository.delete(CREW1);
    }

    @Test
    @DisplayName("코치가 OPEN인 면담 가능한 시간을 조회한다.")
    void findOpenAvailableDateTimesByCoachId() {
        // given
        final LocalDateTime startTime = LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME);
        final LocalDateTime reservedTime = LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME);
        final LocalDateTime availableTime = LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME);

        final AvailableDateTime availableDateTime = saveAvailableTime(availableTime, OPEN);
        saveAvailableTime(startTime, USED);
        final AvailableDateTime reservedDateTime = saveAvailableTime(reservedTime, OPEN);

        // when
        final List<AvailableDateTime> times = availableDateTimeRepository.findOpenAvailableDateTimesByCoachId(
                coach.getId(),
                NOW_PLUS_1_MONTH.getYear(),
                NOW_PLUS_1_MONTH.getMonthValue());

        // then
        final AvailableDateTime expect1 = new AvailableDateTime(reservedDateTime.getId(), coach.getId(),
                reservedTime, OPEN);
        final AvailableDateTime expect2 = new AvailableDateTime(availableDateTime.getId(), coach.getId(),
                availableTime, OPEN);
        assertThat(times).hasSize(2)
                .containsExactly(expect1, expect2);
    }

    @Test
    @DisplayName("해당 코치의 면담가능한 시간과 interview startTime이 포함된 정렬된 AvailableTime List를 반환해야 한다.")
    void findAvailableDateTimesByCoachIdAndInterviewId() {
        // given
        final LocalDateTime startTime = LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME);
        final LocalDateTime reservedTime = LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME);
        final LocalDateTime availableTime = LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME);

        final AvailableDateTime availableDateTime = saveAvailableTime(availableTime, OPEN);
        final AvailableDateTime startDateTime = saveAvailableTime(startTime, USED);
        saveAvailableTime(reservedTime, USED);

        // when
        final Long interviewId = saveInterview(startTime);
        final List<AvailableDateTime> times = availableDateTimeRepository
                .findAvailableDateTimesByCoachIdAndInterviewId(interviewId,
                        coach.getId(),
                        NOW_PLUS_1_MONTH.getYear(),
                        NOW_PLUS_1_MONTH.getMonthValue());

        // then
        assertThat(times).hasSize(2)
                .containsExactly(new AvailableDateTime(startDateTime.getId(), coach.getId(), startTime, USED),
                        new AvailableDateTime(availableDateTime.getId(), coach.getId(), availableTime, OPEN));
    }

    private AvailableDateTime saveAvailableTime(final LocalDateTime startTime,
                                                final AvailableDateTimeStatus availableDateTimeStatus) {
        return availableDateTimeRepository.save(
                new AvailableDateTime(coach.getId(), startTime, availableDateTimeStatus));
    }

    private Long saveInterview(final LocalDateTime startTime) {
        final Interview interview = interviewRepository.save(new Interview(
                startTime,
                startTime.plusMinutes(30),
                coach,
                crew,
                createFormItems()
        ));
        return interview.getId();
    }

    private List<FormItem> createFormItems() {
        return List.of(new FormItem(null, Question.from("고정질문1"), Answer.from("고정답변1")),
                new FormItem(null, Question.from("고정질문1"), Answer.from("고정답변1")),
                new FormItem(null, Question.from("고정질문1"), Answer.from("고정답변1")));
    }
}
