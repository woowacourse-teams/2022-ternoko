package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NEXT_MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CoachServiceTest {

    @Autowired
    private CoachService coachService;

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findCoaches() {
        // when
        final CoachesResponse coaches = coachService.findCoaches();

        // then
        assertThat(coaches.getCoaches()).extracting("nickname")
                .contains("준", "브리", "토미", "네오");
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void putAvailableDateTimesByCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        // whenR
        final List<AvailableDateTime> availableDateTimes = coachService.findAvailableDateTimesByCoachId(COACH3.getId(),
                NOW.getYear(),
                NOW.getMonthValue());

        // then
        assertThat(availableDateTimes).hasSize(9);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간에 빈 리스트를 넣어준다.")
    void putEmptyAvailableDateTimesByCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), new AvailableDateTimesRequest(List.of()));

        // when
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(COACH3.getId(), NOW.getYear(), NOW.getMonthValue());

        // then
        assertThat(availableDateTimes).hasSize(0);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간 저장시 존재하지 않는 코치 id를 넣어줄 경우 예외가 발생한다.")
    void putAvailableDateTimesByInvalidCoachId() {
        assertThatThrownBy(
                () -> coachService.putAvailableDateTimesByCoachId(-1L, new AvailableDateTimesRequest(List.of()))
        ).isInstanceOf(CoachNotFoundException.class);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 조회한다.")
    void findAvailableDateTimesByCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTHS_REQUEST);

        // when
        final List<AvailableDateTime> availableDateTimes = coachService.findAvailableDateTimesByCoachId(COACH3.getId(),
                NOW_PLUS_1_MONTH.getYear(),
                NOW_PLUS_1_MONTH.getMonthValue());

        // then
        assertThat(availableDateTimes.stream()
                .map(AvailableDateTime::getLocalDateTime)
                .collect(Collectors.toList()))
                .hasSize(NEXT_MONTH_REQUEST.getTimes().size())
                .containsAnyElementsOf(NEXT_MONTH_REQUEST.getTimes());
    }
}
