package com.woowacourse.ternoko.fixture;

import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.domain.Type;

public class ReservationFixture {

    public static final Long INTERVIEW_TIME = 30L;

    public static final Member COACH1 = new Member(1L, "준", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH2 = new Member(2L, "브리", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH3 = new Member(3L, "토미", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH4 = new Member(4L, "네오", "test@email.com", Type.COACH, "imageUrl");
}
