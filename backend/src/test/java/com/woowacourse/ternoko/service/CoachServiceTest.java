package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_PLUS_1_MONTH;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.TIME2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.InvalidMemberNicknameException;
import com.woowacourse.ternoko.core.application.CoachService;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.coach.CoachRepository;
import com.woowacourse.ternoko.core.dto.request.CalendarRequest;
import com.woowacourse.ternoko.core.dto.request.CoachUpdateRequest;
import com.woowacourse.ternoko.core.dto.response.CoachResponse;
import com.woowacourse.ternoko.core.dto.response.CoachesResponse;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import com.woowacourse.ternoko.support.utils.ServiceTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class CoachServiceTest extends DatabaseSupporter {

    @Autowired
    private CoachService coachService;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private AvailableDateTimeRepository availableDateTimeRepository;

    @Test
    @DisplayName("코치 정보를 조회한다.")
    void findCoach() {
        Coach coach = new Coach("박재성", "포비", "pobi@woowahan.com", "U1234567898", ".png", "반란군을 키우는 포비");
        Coach savedCoach = coachRepository.save(coach);
        final CoachResponse foundCoach = coachService.findCoach(savedCoach.getId());

        assertAll(
                () -> assertThat(foundCoach.getId()).isEqualTo(savedCoach.getId()),
                () -> assertThat(foundCoach.getName()).isEqualTo(savedCoach.getName()),
                () -> assertThat(foundCoach.getNickname()).isEqualTo(savedCoach.getNickname()),
                () -> assertThat(foundCoach.getEmail()).isEqualTo(savedCoach.getEmail()),
                () -> assertThat(foundCoach.getImageUrl()).isEqualTo(savedCoach.getImageUrl()),
                () -> assertThat(foundCoach.getIntroduce()).isEqualTo(savedCoach.getIntroduce())
        );
        coachRepository.delete(savedCoach);
    }

    @Test
    @DisplayName("코치가 되는 시간 카운트가 오늘 23:59까지 제외 되는지 확인한다. 내일 00:00 ~ 한달 뒤 00:00 시 범위에서 이뤄진다.")
    void countAvailableDateTimeByCoachId() {
        Coach coach = new Coach("박재성", "포비", "pobi@woowahan.com", "U1234567898", ".png", "반란군을 키우는 포비");
        Coach savedCoach = coachRepository.save(coach);
        final LocalDateTime 당일_정각_직전 = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));

        final AvailableDateTime saveTime = availableDateTimeRepository.save(
                new AvailableDateTime(savedCoach.getId(), 당일_정각_직전, AvailableDateTimeStatus.OPEN));

        final CoachResponse foundCoach = coachService.findCoach(savedCoach.getId());

        availableDateTimeRepository.delete(saveTime);
        coachRepository.delete(savedCoach);

        Assertions.assertFalse(foundCoach.isHasOpenTime());
    }

    @Test
    @DisplayName("코치가 되는 시간 카운트가 내일 00:00 부터 시작한다.")
    void countAvailableDateTimeByCoachId_tomorrow_true() {
        Coach coach = new Coach("박재성", "포비", "pobi2@woowahan.com", "U1234567898", ".png", "반란군을 키우는 포비");
        Coach savedCoach = coachRepository.save(coach);
        final LocalDateTime 내일_정각 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(00, 00));

        final AvailableDateTime countTime = availableDateTimeRepository.save(
                new AvailableDateTime(savedCoach.getId(), 내일_정각, AvailableDateTimeStatus.OPEN));

        final CoachResponse foundCoach = coachService.findCoach(savedCoach.getId());

        availableDateTimeRepository.delete(countTime);
        coachRepository.delete(savedCoach);

        Assertions.assertTrue(foundCoach.isHasOpenTime());
    }

    @Test
    @DisplayName("코치가 되는 시간 카운트가 내일 00:00 ~ 한달 뒤 00:00 시 범위에서 이뤄진다.")
    void countAvailableDateTimeByCoachId_plus_one_month_true() {
        Coach coach = new Coach("박재성3", "포비3", "pobi3@woowahan.com", "U1234567898", ".png", "반란군을 키우는 포비");
        Coach savedCoach = coachRepository.save(coach);
        final LocalDateTime 한달뒤_정각 = LocalDateTime.of(LocalDate.now().plusMonths(1), LocalTime.of(00, 00));

        final AvailableDateTime countTime = availableDateTimeRepository.save(
                new AvailableDateTime(savedCoach.getId(), 한달뒤_정각, AvailableDateTimeStatus.OPEN));

        final CoachResponse foundCoach = coachService.findCoach(savedCoach.getId());

        availableDateTimeRepository.delete(countTime);
        coachRepository.delete(coach);

        Assertions.assertTrue(foundCoach.isHasOpenTime());
    }

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findCoaches() {
        // when
        final CoachesResponse coaches = coachService.findCoaches();

        // then
        assertThat(coaches.getCoaches()).extracting("nickname")
                .hasSize(4)
                .contains("준", "브리", "토미", "네오");
    }

    @Test
    @DisplayName("slack 회원가입 후 닉네임과 이미지, 소개를 입력받아 partUpdate 한다")
    void updateCoach() {
        //given
        final String imageUrl = ".png";
        final String nickname = "도깨비";
        final String introduce = "안녕하세요. 도깨비 입니다.";
        final String userId = "U223456789";

        final Coach savedCoach = coachRepository.save(
                new Coach("공지철", "공유", " share@woowahan.com", userId, imageUrl, "null"));

        //when
        coachService.partUpdateCoach(savedCoach.getId(), new CoachUpdateRequest(nickname, imageUrl, introduce));
        final CoachResponse foundCoach = coachService.findCoach(savedCoach.getId());

        //then
        assertAll(
                () -> assertThat(foundCoach.getNickname()).isEqualTo(nickname),
                () -> assertThat(foundCoach.getIntroduce()).isEqualTo(introduce)
        );

        coachRepository.delete(savedCoach);
    }

    @Test
    @DisplayName("slack 회원가입 후 닉네임 변경 시 동일한 닉네임으로 변경할 수 있다.")
    void updateCoachSameNickName() {
        //given
        final String imageUrl = ".png";
        final String nickname = "공유";
        final String introduce = "안녕하세요. 도깨비 입니다.";
        final String userId = "U223456789";

        final Coach savedCoach = coachRepository.save(
                new Coach("공지철", "공유", " share@woowahan.com", userId, imageUrl, "null"));

        //when
        coachService.partUpdateCoach(savedCoach.getId(), new CoachUpdateRequest(nickname, imageUrl, introduce));
        final CoachResponse foundCoach = coachService.findCoach(savedCoach.getId());

        //then
        assertAll(
                () -> assertThat(foundCoach.getNickname()).isEqualTo(nickname),
                () -> assertThat(foundCoach.getIntroduce()).isEqualTo(introduce)
        );

        coachRepository.delete(savedCoach);
    }

    @Test
    @DisplayName("slack 회원가입 후 닉네임변경 시 본인의 닉네임이 아니면서 이미 존재하는 회원 닉네임이면 예외를 반환한다.")
    void partUpdateCrewException() {
        // given
        final String imageUrl = ".png";
        final String nickname = COACH1.getNickname();
        final String introduce = "안녕하세요. 도깨비 입니다.";
        final String userId = "U223456789";

        final Coach savedCoach = coachRepository.save(
                new Coach("공지철", "도깨비", " share@woowahan.com", userId, imageUrl, "null"));

        //when, then
        assertThatThrownBy(() -> coachService.partUpdateCoach(savedCoach.getId(),
                new CoachUpdateRequest(nickname, imageUrl, introduce)))
                .isInstanceOf(InvalidMemberNicknameException.class);

        coachRepository.delete(savedCoach);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void putAvailableDateTimesByCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTH_REQUEST);

        // whenR
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(COACH3.getId(), NOW.getYear(), NOW.getMonthValue());

        // then
        assertThat(availableDateTimes).hasSize(9);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간에 빈 리스트를 넣어준다.")
    void putEmptyAvailableDateTimesByCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), new CalendarRequest(List.of()));

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
                () -> coachService.putAvailableDateTimesByCoachId(-1L, new CalendarRequest(List.of())))
                .isInstanceOf(CoachNotFoundException.class);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 조회한다.")
    void findAvailableDateTimesByCoachId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTHS_REQUEST);

        // when
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(COACH3.getId(), NOW_PLUS_1_MONTH.getYear(),
                        NOW_PLUS_1_MONTH.getMonthValue());

        // then
        assertThat(availableDateTimes.stream()
                .map(AvailableDateTime::getLocalDateTime)
                .collect(Collectors.toList()))
                .hasSize(3);
    }

    @Test
    @DisplayName("크루 면담 수정시 코치의 면담 가능 시간을 조회한다.")
    void findAvailableDateTimesByCoachIdAndInterviewId() {
        // given
        coachService.putAvailableDateTimesByCoachId(COACH3.getId(), MONTHS_REQUEST);

        // when
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(COACH3.getId(), NOW_PLUS_1_MONTH.getYear(),
                        NOW_PLUS_1_MONTH.getMonthValue());

        // then
        assertThat(availableDateTimes.stream()
                .map(AvailableDateTime::getLocalDateTime)
                .collect(Collectors.toList()))
                .hasSize(3);
    }
}
