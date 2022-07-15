package com.woowacourse.ternoko.fixture;

import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.domain.Type;
import java.time.LocalDateTime;
import java.util.List;

public class MemberFixture {

    public static final Member COACH1 = new Member(1L, "준", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH2 = new Member(2L, "브리", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH3 = new Member(3L, "토미", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH4 = new Member(4L, "네오", "test@email.com", Type.COACH, "imageUrl");

    public static final LocalDateTime time2 = LocalDateTime.now().plusDays(2);
    public static final LocalDateTime time3 = LocalDateTime.now().plusDays(3);
    public static final LocalDateTime time4 = LocalDateTime.now().plusDays(4);

    public static final List<LocalDateTime> AVAILABLE_TIMES = List.of(
            LocalDateTime.of(time2.getYear(), time2.getMonthValue(),
                    time2.getDayOfMonth(), time2.getHour(), time2.getMinute()),
            LocalDateTime.of(time3.getYear(), time3.getMonthValue(),
                    time3.getDayOfMonth(), time3.getHour(), time3.getMinute()),
            LocalDateTime.of(time4.getYear(), time4.getMonthValue(),
                    time4.getDayOfMonth(), time4.getHour(), time4.getMinute()));
}
