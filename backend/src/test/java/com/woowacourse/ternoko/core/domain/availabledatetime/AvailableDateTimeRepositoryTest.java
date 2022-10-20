package com.woowacourse.ternoko.core.domain.availabledatetime;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    @DisplayName("해당 연, 월에 해당하는 면담가능한 시간중 OPEN 만 삭제한다.")
    void deleteAllByCoachAndYearAndMonthAndOpen() {
        // given
        saveAvailableTime(LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME), OPEN);
        saveAvailableTime(LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME), OPEN);
        saveAvailableTime(LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME), USED);

        // when
        availableDateTimeRepository.deleteAllByCoachAndYearAndMonthAndStatus(coach.getId(),
                NOW.getYear(),
                NOW_PLUS_1_MONTH.getMonthValue(),
                OPEN);
        final List<AvailableDateTime> availableDateTimes = availableDateTimeRepository.findAllByCoachId(coach.getId());

        // then
        assertThat(availableDateTimes.size()).isEqualTo(1);
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
        assertThat(times.stream()
                .map(AvailableDateTime::getId)
                .collect(Collectors.toList())).hasSize(2)
                .containsExactly(expect1.getId(), expect2.getId());
    }

    @Test
    @DisplayName("코치가 OPEN인 면담 가능한 시간을 조회한다.")
    void findOpenAndUsedAvailableDateTimesByCoachId() {
        // given
        final LocalDateTime startTime = LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME);
        final LocalDateTime reservedTime = LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME);
        final LocalDateTime availableTime = LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME);

        final AvailableDateTime startDateTime = saveAvailableTime(startTime, USED);
        final AvailableDateTime availableDateTime = saveAvailableTime(availableTime, OPEN);
        final AvailableDateTime reservedDateTime = saveAvailableTime(reservedTime, OPEN);

        // when
        final List<AvailableDateTime> times = availableDateTimeRepository.findOpenAndUsedAvailableDateTimesByCoachId(
                coach.getId(),
                NOW_PLUS_1_MONTH.getYear(),
                NOW_PLUS_1_MONTH.getMonthValue());

        // then
        final AvailableDateTime expect2 = new AvailableDateTime(reservedDateTime.getId(), coach.getId(),
                reservedTime, OPEN);
        final AvailableDateTime expect3 = new AvailableDateTime(availableDateTime.getId(), coach.getId(),
                availableTime, OPEN);
        final AvailableDateTime expect1 = new AvailableDateTime(startDateTime.getId(), coach.getId(),
                startTime, USED);
        assertThat(times.stream()
                .map(AvailableDateTime::getId)
                .collect(Collectors.toList())).hasSize(3)
                .containsExactly(expect1.getId(), expect2.getId(), expect3.getId());
    }

    @Test
    @DisplayName("코치의 되는 시간 여부를 한달 단위로 반환한다.")
    void countByCoachId_1day() {
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = start.plusMonths(1);
        final LocalDateTime startTime = LocalDateTime.of(LocalDate.from(start.plusDays(1)), FIRST_TIME);
        final LocalDateTime reservedTime = LocalDateTime.of(LocalDate.from(start.plusDays(32)), SECOND_TIME);
        availableDateTimeRepository.save(new AvailableDateTime(COACH2.getId(), reservedTime, OPEN));
        availableDateTimeRepository.save(new AvailableDateTime(COACH2.getId(), startTime, OPEN));

        final Long size = availableDateTimeRepository.countByCoachId(COACH2.getId(), start, end);

        assertThat(size).isEqualTo(1);
    }

    private AvailableDateTime saveAvailableTime(final LocalDateTime startTime,
                                                final AvailableDateTimeStatus availableDateTimeStatus) {
        return availableDateTimeRepository.save(
                new AvailableDateTime(coach.getId(), startTime, availableDateTimeStatus));
    }
}
