package com.woowacourse.ternoko.domain;

public enum InterviewStatusType {
    EDITABLE,
    FIXED,
    COMMENT,
    COACH_COMPLETED,
    CREW_COMPLETED,
    COMPLETE,
    CANCELED;

    public static boolean isCanceled(InterviewStatusType type) {
        return CANCELED.equals(type);
    }

}
