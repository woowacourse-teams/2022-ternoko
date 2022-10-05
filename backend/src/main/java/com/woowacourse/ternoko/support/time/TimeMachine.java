package com.woowacourse.ternoko.support.time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class TimeMachine {
    private static Clock clock = Clock.systemDefaultZone();

    private static ZoneOffset zoneOffset = ZoneOffset.UTC;

    public static LocalDateTime dateTimeOfNow() {
        return LocalDateTime.now(clock);
    }

    public static LocalTime timeOfNow() {
        return LocalTime.now(clock);
    }

    public static LocalDate dateOfNow() {
        return LocalDate.now(clock);
    }

    public static void timeTravelAt(LocalDateTime dateTime) {
        clock = Clock.fixed(dateTime.atOffset(zoneOffset).toInstant(), zoneOffset);
    }

    public static void timeTravelAt(LocalTime time) {
        clock = Clock.fixed(time.atDate(LocalDate.now()).atOffset(zoneOffset).toInstant(), zoneOffset);
    }

    public static void timeTravelAt(LocalDate date) {
        clock = Clock.fixed(date.atStartOfDay().atOffset(zoneOffset).toInstant(), zoneOffset);
    }

    public static void reset() {
        clock = Clock.systemDefaultZone();
    }
}
