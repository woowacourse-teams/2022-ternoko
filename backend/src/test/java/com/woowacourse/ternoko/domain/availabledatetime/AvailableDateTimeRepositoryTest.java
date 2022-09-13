package com.woowacourse.ternoko.domain.availabledatetime;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
import com.woowacourse.ternoko.core.domain.interview.formitem.Answer;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.interview.formitem.Question;
import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import java.time.LocalDate;
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
    @DisplayName("당일을 제외하고, 코치가 OPEN인 면담 가능한 시간을 조회한다.")
    void findOpenAvailableDateTimesByCoachId_expect_today() {
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
    @DisplayName("코치의 되는 시간 여부를 당일을 제외하고, 한달 단위로 반환한다.")
    void countByCoachId_1day_expect_today() {
        final LocalDateTime start = LocalDateTime.of(LocalDate.from(LocalDateTime.now()), FIRST_TIME);
        final LocalDateTime end = start.plusMonths(1);
        final LocalDateTime startTime = LocalDateTime.of(LocalDate.from(start.plusDays(1)), THIRD_TIME);
        final LocalDateTime reservedTime = LocalDateTime.of(LocalDate.from(start.plusDays(32)), SECOND_TIME);
        availableDateTimeRepository.save(new AvailableDateTime(COACH2.getId(), reservedTime, OPEN));
        availableDateTimeRepository.save(new AvailableDateTime(COACH2.getId(), startTime, OPEN));
        availableDateTimeRepository.save(new AvailableDateTime(COACH2.getId(), start, OPEN));

        final Long size = availableDateTimeRepository.countByCoachId(COACH2.getId(), start.plusDays(1), end);

        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("해당 코치의 면담가능한 시간과 interview startTime이 포함된 정렬된 AvailableTime List를 반환해야 한다.")
    void findByCoachIdAndYearAndMonthAndOpenOrInterviewStartTime() {
        // given
        final LocalDateTime startTime = LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME);
        final LocalDateTime reservedTime = LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME);
        final LocalDateTime availableTime = LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME);

        availableDateTimeRepository.save(new AvailableDateTime(COACH2.getId(), startTime, OPEN));
        final AvailableDateTime availableDateTime = saveAvailableTime(availableTime, OPEN);
        final AvailableDateTime startDateTime = saveAvailableTime(startTime, USED);
        saveAvailableTime(reservedTime, USED);

        // when
        final Long interviewId = saveInterview(startTime);
        final List<AvailableDateTime> times = availableDateTimeRepository
                .findByCoachIdAndYearAndMonthAndOpenOrInterviewStartTime(interviewId,
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
