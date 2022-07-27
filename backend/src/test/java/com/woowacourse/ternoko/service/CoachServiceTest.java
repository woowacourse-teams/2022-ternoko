package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.fixture.MemberFixture.AVAILABLE_TIMES;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.TIME2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachUpdateRequest;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import com.woowacourse.ternoko.repository.CoachRepository;
import java.time.LocalDateTime;
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

    @Autowired
    private CoachRepository coachRepository;

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
    @DisplayName("slack 회원가입 후 닉네임과 이미지, 소개를 입력받아 partUpdate 한다")
    void updateCoach() {
        //given
        final String imageUrl = ".png";
        final String nickname = "도깨비";
        final String introduce = "안녕하세요. 도깨비 입니다.";

        final Coach savedCoach = coachRepository.save(new Coach("공유", " share@woowahan.com", imageUrl));

        //when
        coachService.partUpdateCrew(savedCoach.getId(), new CoachUpdateRequest(nickname, imageUrl, introduce));
        final CoachResponse foundCoach = coachService.findCoach(savedCoach.getId());

        //then
        assertAll(
                () -> assertThat(foundCoach.getNickname()).isEqualTo(nickname),
                () -> assertThat(foundCoach.getIntroduce()).isEqualTo(introduce)
        );
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void putAvailableDateTimesByCoachId() {
        // given
        int year = TIME2.getYear();
        int month = TIME2.getMonthValue();
        final AvailableDateTimeRequest availableDateTimeRequest = new AvailableDateTimeRequest(
                year,
                month,
                AVAILABLE_TIMES);

        coachService.putAvailableDateTimesByCoachId(COACH3.getId(),
                new AvailableDateTimesRequest(List.of(availableDateTimeRequest)));

        // whenR
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(COACH3.getId(), year, month);

        // then
        assertThat(availableDateTimes).hasSize(3);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간에 빈 리스트를 넣어준다.")
    void putEmptyAvailableDateTimesByCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), new AvailableDateTimesRequest(List.of()));

        // when
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(COACH3.getId(), TIME2.getYear(), TIME2.getMonthValue());

        // then
        assertThat(availableDateTimes).hasSize(0);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간 저장시 존재하지 않는 코치 id를 넣어줄 경우 예외가 발생한다.")
    void putAvailableDateTimesByInvalidCoachId() {
        assertThatThrownBy(
                () -> coachService.putAvailableDateTimesByCoachId(-1L, new AvailableDateTimesRequest(List.of())))
                .isInstanceOf(CoachNotFoundException.class);
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
        final LocalDateTime nextMonthDatTime = LocalDateTime.now().plusMonths(1);
        final AvailableDateTimeRequest nextMonthAvailableDateTimeRequest = new AvailableDateTimeRequest(
                nextMonthDatTime.getYear(),
                nextMonthDatTime.getMonthValue(),
                List.of(nextMonthDatTime));

        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), new AvailableDateTimesRequest(
                List.of(availableDateTimeRequest,
                        nextMonthAvailableDateTimeRequest)));

        // when
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(COACH3.getId(), TIME2.getYear(), TIME2.getMonthValue());

        // then
        assertThat(availableDateTimes.stream()
                .map(AvailableDateTime::getLocalDateTime)
                .collect(Collectors.toList()))
                .hasSize(times.size())
                .containsAnyElementsOf(times);
    }
}
