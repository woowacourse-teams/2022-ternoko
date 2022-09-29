package com.woowacourse.ternoko.support.fixture.refactor.timemachine;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

public class ClockTest {

    @Test
    public void testFixedClock() {
        // 2011년 1월 1일 1시 1분 0초
        LocalDateTime dateTime = LocalDateTime.of(2011, 1, 1, 1, 1);

        // UTC +09:00 서울/도쿄 기준 2011년 1월 1일 1시 1분 0초
        Instant instant = dateTime.atOffset(ZoneOffset.ofHours(9)).toInstant();

        // UTC +09:00 2011년 1월 1일 1시 1분 0초를 UTC ±00:00 기준으로 변환한 Clock
        Clock fixedClock = Clock.fixed(instant, ZoneOffset.ofHours(0));

        // UTC +09:00 기준의 시간을 UTC ±00:00 기준의 시간으로 변경했으므로 9시간만 빼면 된다.
        assertThat(LocalDateTime.now(fixedClock)).isEqualTo(LocalDateTime.of(2010, 12, 31, 16, 1));
    }
}
