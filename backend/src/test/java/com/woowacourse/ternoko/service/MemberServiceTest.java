package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findCoaches() {
        // when
        final CoachesResponse coaches = memberService.findCoaches();

        // then
        assertThat(coaches.getCoaches()).extracting("nickname")
                .contains("준", "브리", "토미", "네오");
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void putAvailableDateTimesByCoachId() {
        // given
        final AvailableDateTimeRequest availableDateTimeRequest = new AvailableDateTimeRequest(
            TIME2.getYear(),
            TIME2.getMonthValue(),
            AVAILABLE_TIMES);

        memberService.putAvailableDateTimesByCoachId(COACH3.getId(), new AvailableDateTimesRequest(List.of(availableDateTimeRequest)));

        // whenR
        final List<AvailableDateTime> availableDateTimes = memberService.findAvailableDateTimesByCoachId(COACH3.getId());

        // then
        assertThat(availableDateTimes).hasSize(3);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간에 빈 리스트를 넣어준다.")
    void putEmptyAvailableDateTimesByCoachId() {
        // given
        memberService.putAvailableDateTimesByCoachId(COACH3.getId(), new AvailableDateTimesRequest(List.of()));

        // when
        final List<AvailableDateTime> availableDateTimes = memberService.findAvailableDateTimesByCoachId(COACH3.getId());

        // then
        assertThat(availableDateTimes).hasSize(0);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간 저장시 존재하지 않는 코치 id를 넣어줄 경우 예외가 발생한다.")
    void putAvailableDateTimesByInvalidCoachId() {
        assertThatThrownBy(
                () -> memberService.putAvailableDateTimesByCoachId(-1L, new AvailableDateTimesRequest(List.of())))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 조회한다.")
    void findAvailableDateTimesByCoachId() {
        // given
        final List<LocalDateTime> times = AVAILABLE_TIMES;
        final AvailableDateTimeRequest availableDateTimeRequest = new AvailableDateTimeRequest(
            TIME2.getYear(),
            TIME2.getMonthValue(),
            times);

        memberService.putAvailableDateTimesByCoachId(COACH3.getId(), new AvailableDateTimesRequest(List.of(availableDateTimeRequest)));

        // when
        final List<AvailableDateTime> availableDateTimes = memberService.findAvailableDateTimesByCoachId(
                COACH3.getId());

        // then
        assertThat(availableDateTimes.stream()
                .map(AvailableDateTime::getLocalDateTime)
                .collect(Collectors.toList()))
                .hasSize(3)
                .containsAnyElementsOf(times);
    }
}
