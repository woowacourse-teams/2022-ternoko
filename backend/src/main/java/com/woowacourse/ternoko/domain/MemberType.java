package com.woowacourse.ternoko.domain;

import java.util.Arrays;

public enum MemberType {
    COACH,
    CREW;

    public static boolean isCoachType(final String value) {
        return COACH.name().equals(value);
    }

    public static boolean isCrewType(final String value) {
        return CREW.name().equals(value);
    }

    public static boolean existType(final String value) {
        return Arrays.stream(values())
                .anyMatch(type -> type.name().equals(value));
    }
}


