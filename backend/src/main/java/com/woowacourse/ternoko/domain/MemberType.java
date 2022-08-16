package com.woowacourse.ternoko.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum MemberType {
    COACH,
    CREW;

    public boolean matchType(final String value) {
        return this.name().equals(value.toUpperCase(Locale.ROOT));
    }

    public static boolean existType(final String value) {
        return Arrays.stream(values())
                .anyMatch(type -> type.name().equals(value));
    }

    public List<InterviewStatusType> getValidCreateCommentStatusType() {
        if (this == COACH) {
            return List.of(InterviewStatusType.COMMENT, InterviewStatusType.CREW_COMPLETED);
        }
        return List.of(InterviewStatusType.COMMENT, InterviewStatusType.COACH_COMPLETED);
    }

    public List<InterviewStatusType> getValidFindCommentStatusType() {
        if (this == COACH) {
            return List.of(InterviewStatusType.COMPLETE, InterviewStatusType.COACH_COMPLETED);
        }
        return List.of(InterviewStatusType.COMPLETE, InterviewStatusType.CREW_COMPLETED);
    }
}


