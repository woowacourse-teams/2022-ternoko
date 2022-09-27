package com.woowacourse.ternoko.support.fixture.refactor;

import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;

public class MemberFixture {
    public static Coach 준 = createCoach(1L, "test1@woowahan.com", "준", "U1234567891");
    public static Coach 브리 = createCoach(2L, "test2@woowahan.com", "브리", "U1234567892");
    public static Coach 토미 = createCoach(3L, "test3@woowahan.com", "토미", "U1234567893");
    public static Coach 네오 = createCoach(4L, "test4@woowahan.com", "네오", "U1234567894");

    public static Crew 허수달 = createCrew(5L, "test5@gmail.com", "허수달", "U1234567895");
    public static Crew 손앤지 = createCrew(6L, "test6@gmail.com", "손앤지", "U1234567896");
    public static Crew 김애쉬 = createCrew(7L, "test7@gmail.com", "김애쉬", "U1234567897");
    public static Crew 김록바 = createCrew(8L, "test8@gmail.com", "김록바", "U1234567898");

    private static Coach createCoach(final Long id,
                                     final String email,
                                     final String nickname,
                                     final String userId) {
        return new Coach(id, "이름", nickname, email, userId, "imageUrl", "안녕하세요.");
    }

    private static Crew createCrew(final Long id,
                                   final String email,
                                   final String nickname,
                                   final String userId) {
        return new Crew(id, "이름", nickname, email, userId, "imageUrl");
    }
}
