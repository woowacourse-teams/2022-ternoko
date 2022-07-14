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

    public static final List<LocalDateTime> AVAILABLE_TIMES = List.of(
            LocalDateTime.of(2022, 7, 7, 14, 0),
            LocalDateTime.of(2022, 7, 7, 15, 0),
            LocalDateTime.of(2022, 7, 7, 16, 0));
}
