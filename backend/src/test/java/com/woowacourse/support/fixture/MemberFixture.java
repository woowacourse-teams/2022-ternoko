package com.woowacourse.support.fixture;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.OPEN;

import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeRequest;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.dto.CoachUpdateRequest;
import com.woowacourse.ternoko.dto.CrewUpdateRequest;
import java.time.LocalDateTime;
import java.util.List;

public class MemberFixture {

    public static final Coach COACH1 = new Coach(1L, "이름", "준", "test1@woowahan.com", "U1234567891", "imageUrl",
            "안녕하세요.");
    public static final Coach COACH2 = new Coach(2L, "이름", "브리", "test2@woowahan.com", "U1234567892", "imageUrl",
            "안녕하세요.");
    public static final Coach COACH3 = new Coach(3L, "이름", "토미", "test3@woowahan.com", "U1234567893", "imageUrl",
            "안녕하세요.");
    public static final Coach COACH4 = new Coach(4L, "이름", "네오", "test4@woowahan.com", "U1234567894", "imageUrl",
            "안녕하세요.");
    public static final Crew CREW1 = new Crew(5L, "허수진", "수달", "test5@woowahan.com", "U1234567895", "imageUrl");
    public static final Crew CREW2 = new Crew(6L, "손수민", "앤지", "test6@email.com", "U1234567896", "imageUrl");
    public static final Crew CREW3 = new Crew(7L, "김동호", "애쉬", "test7@email.com", "U1234567897", "imageUrl");
    public static final Crew CREW4 = new Crew(8L, "김상록", "록바", "test8@email.com", "U1234567898", "imageUrl");

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
