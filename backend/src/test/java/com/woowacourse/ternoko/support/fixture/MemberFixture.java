package com.woowacourse.ternoko.support.fixture;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;

import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.core.dto.request.CoachUpdateRequest;
import com.woowacourse.ternoko.core.dto.request.CrewUpdateRequest;
import java.time.LocalDateTime;
import java.util.List;

public class MemberFixture {

    public static Coach COACH1 = createCoach(1L, "test1@woowahan.com", "준", "U1234567891");
    public static Coach COACH2 = createCoach(2L, "test2@woowahan.com", "브리", "U1234567892");
    public static Coach COACH3 = createCoach(3L, "test3@woowahan.com", "토미", "U1234567893");
    public static Coach COACH4 = createCoach(4L, "test4@woowahan.com", "네오", "U1234567894");

    private static Coach createCoach(final Long id,
                                     final String email,
                                     final String nickname,
                                     final String userId) {
        return new Coach(id, "이름", nickname, email, userId, "imageUrl", "안녕하세요.");
    }

    public static Crew CREW1 = createCrew(5L, "test5@gmail.com", "허수달", "U1234567895");
    public static Crew CREW2 = createCrew(6L, "test6@gmail.com", "손앤지", "U1234567896");
    public static Crew CREW3 = createCrew(7L, "test7@gmail.com", "김애쉬", "U1234567897");
    public static Crew CREW4 = createCrew(8L, "test8@gmail.com", "김록바", "U1234567898");

    private static Crew createCrew(final Long id,
                                   final String email,
                                   final String nickname,
                                   final String userId) {
        return new Crew(id, "이름", nickname, email, userId, "imageUrl");
    }

    public static final LocalDateTime TIME2 = LocalDateTime.now().plusDays(2);
    public static final LocalDateTime TIME3 = LocalDateTime.now().plusDays(3);
    public static final LocalDateTime TIME4 = LocalDateTime.now().plusDays(4);

    public static final List<AvailableDateTimeSummaryRequest> AVAILABLE_TIMES = List.of(
            new AvailableDateTimeSummaryRequest(LocalDateTime.of(TIME2.getYear(), TIME2.getMonthValue(),
                    TIME2.getDayOfMonth(), TIME2.getHour(), TIME2.getMinute()), OPEN),
            new AvailableDateTimeSummaryRequest(LocalDateTime.of(TIME3.getYear(), TIME3.getMonthValue(),
                    TIME3.getDayOfMonth(), TIME3.getHour(), TIME3.getMinute()), OPEN),
            new AvailableDateTimeSummaryRequest(LocalDateTime.of(TIME4.getYear(), TIME4.getMonthValue(),
                    TIME4.getDayOfMonth(), TIME4.getHour(), TIME4.getMinute()), OPEN));

    public static final AvailableDateTimeRequest AVAILABLE_DATE_TIME_REQUEST2 = new AvailableDateTimeRequest(
            TIME2.getYear(),
            TIME2.getMonthValue(),
            AVAILABLE_TIMES);

    public static final AvailableDateTimeRequest AVAILABLE_DATE_TIME_REQUEST3 = new AvailableDateTimeRequest(
            TIME3.getYear(),
            TIME3.getMonthValue(),
            AVAILABLE_TIMES);

    public static final AvailableDateTimeRequest AVAILABLE_DATE_TIME_REQUEST4 = new AvailableDateTimeRequest(
            TIME4.getYear(),
            TIME4.getMonthValue(),
            AVAILABLE_TIMES);

    public static final CoachUpdateRequest COACH1_UPDATE_REQUEST = new CoachUpdateRequest(COACH1.getNickname(),
            COACH1.getImageUrl(), "행복하세요.");

    public static final CrewUpdateRequest CREW2_UPDATE_REQUEST = new CrewUpdateRequest(CREW2.getNickname(),
            "newImageUrl");
}
