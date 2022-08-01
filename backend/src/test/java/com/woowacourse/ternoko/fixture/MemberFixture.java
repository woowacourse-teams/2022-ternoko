package com.woowacourse.ternoko.fixture;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import java.time.LocalDateTime;
import java.util.List;

public class MemberFixture {

    public static final Member COACH1 = new Coach(1L, "이름", "준", "test1@woowahan.com", "U1234567891", "imageUr",
            "안녕하세요.");
    public static final Member COACH2 = new Coach(2L, "이름", "브리", "test2@woowahan.com", "U1234567892", "imageUrl",
            "안녕하세요.");
    public static final Member COACH3 = new Coach(3L, "이름", "토미", "test3@woowahan.com", "U1234567893", "imageUrl",
            "안녕하세요.");
    public static final Member COACH4 = new Coach(4L, "이름", "네오", "test4@woowahan.com", "U1234567894", "imageUrl",
            "안녕하세요.");
    public static final Member CREW1 = new Crew(5L, "허수진", "수달", "test5@woowahan.com", "U1234567895", "imageUrl");
    public static final Member CREW2 = new Crew(6L, "손수민", "앤지", "test6@email.com", "U1234567896", "imageUrl");
    public static final Member CREW3 = new Crew(7L, "김동호", "애쉬", "test7@email.com", "U1234567897", "imageUrl");
    public static final Member CREW4 = new Crew(8L, "김상록", "록바", "test8@email.com", "U1234567898", "imageUrl");

    public static final LocalDateTime TIME2 = LocalDateTime.now().plusDays(2);
    public static final LocalDateTime TIME3 = LocalDateTime.now().plusDays(3);
    public static final LocalDateTime TIME4 = LocalDateTime.now().plusDays(4);

    public static final List<LocalDateTime> AVAILABLE_TIMES = List.of(
            LocalDateTime.of(TIME2.getYear(), TIME2.getMonthValue(),
                    TIME2.getDayOfMonth(), TIME2.getHour(), TIME2.getMinute()),
            LocalDateTime.of(TIME3.getYear(), TIME3.getMonthValue(),
                    TIME3.getDayOfMonth(), TIME3.getHour(), TIME3.getMinute()),
            LocalDateTime.of(TIME4.getYear(), TIME4.getMonthValue(),
                    TIME4.getDayOfMonth(), TIME4.getHour(), TIME4.getMinute()));

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
}
