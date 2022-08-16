package com.woowacourse.ternoko.availabledatetime.domain;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import java.time.LocalDateTime;
import java.util.List;
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

    @Test
    @DisplayName("코치가 OPEN인 면담 가능한 시간을 조회한다.")
    void findOpenAvailableDateTimesByCoachId() {
        // given
        final LocalDateTime startTime = LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME);
        final LocalDateTime reservedTime = LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME);
        final LocalDateTime availableTime = LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME);

        final AvailableDateTime availableDateTime = saveAvailableTime(availableTime);
        final AvailableDateTime startDateTime = saveAvailableTime(startTime);
        final AvailableDateTime reservedDateTime = saveAvailableTime(reservedTime);
        startDateTime.changeStatus(USED);

        // when
        saveInterview(startTime);
        final List<AvailableDateTime> times = availableDateTimeRepository.findOpenAvailableDateTimesByCoachId(
                COACH1.getId(),
                NOW_PLUS_1_MONTH.getYear(),
                NOW_PLUS_1_MONTH.getMonthValue());

        // then
        final AvailableDateTime expect1 = new AvailableDateTime(reservedDateTime.getId(), COACH1.getId(),
                reservedTime, OPEN);
        final AvailableDateTime expect2 = new AvailableDateTime(availableDateTime.getId(), COACH1.getId(),
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

        final AvailableDateTime availableDateTime = saveAvailableTime(availableTime);
        final AvailableDateTime startDateTime = saveAvailableTime(startTime);
        startDateTime.changeStatus(USED);
        final AvailableDateTime reservedDateTime = saveAvailableTime(reservedTime);
        reservedDateTime.changeStatus(USED);

        // when
        final Long interviewId = saveInterview(startTime);
        final List<AvailableDateTime> times = availableDateTimeRepository
                .findAvailableDateTimesByCoachIdAndInterviewId(interviewId,
                COACH1.getId(),
                NOW_PLUS_1_MONTH.getYear(),
                NOW_PLUS_1_MONTH.getMonthValue());

        // then
        assertThat(times).hasSize(2)
                .containsExactly(new AvailableDateTime(startDateTime.getId(), COACH1.getId(), startTime, USED),
                        new AvailableDateTime(availableDateTime.getId(), COACH1.getId(), availableTime, OPEN));

    }

    private AvailableDateTime saveAvailableTime(final LocalDateTime startTime) {
        return availableDateTimeRepository.save(new AvailableDateTime(COACH1.getId(), startTime, OPEN));
    }

    private Long saveInterview(final LocalDateTime startTime) {
        final Interview interview = interviewRepository.save(new Interview(
                startTime,
                startTime.plusMinutes(30),
                COACH1,
                CREW1
        ));
        return interview.getId();
    }
}
