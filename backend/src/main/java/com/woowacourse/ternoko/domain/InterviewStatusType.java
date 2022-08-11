package com.woowacourse.ternoko.domain;

public enum InterviewStatusType {
    EDITABLE,
    FIX,
    FEEDBACK,
    COMPLETED,
    CANCELED;

    public static boolean isCanceled(InterviewStatusType type) {
        return CANCELED.equals(type);
    }
}
