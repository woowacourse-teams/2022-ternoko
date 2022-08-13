package com.woowacourse.ternoko.domain;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_FIND_COMMENT;

import com.woowacourse.ternoko.comment.exception.InvalidStatusCreateCommentException;
import com.woowacourse.ternoko.comment.exception.InvalidStatusFindCommentException;
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

    public void validateCreateCommentStatus(final InterviewStatusType interviewStatusType) {
        if (!getValidCreateCommentStatusType().contains(interviewStatusType)) {
            throw new InvalidStatusCreateCommentException(INVALID_STATUS_CREATE_COMMENT);
        }
    }

    public void validateFindCommentStatus(final InterviewStatusType interviewStatusType) {
        if (!getValidFindCommentStatusType().contains(interviewStatusType)) {
            throw new InvalidStatusFindCommentException(INVALID_STATUS_FIND_COMMENT);
        }
    }

    private List<InterviewStatusType> getValidCreateCommentStatusType() {
        if (this == COACH) {
            return List.of(InterviewStatusType.COMMENT, InterviewStatusType.CREW_COMPLETED);
        }
        return List.of(InterviewStatusType.COMMENT, InterviewStatusType.COACH_COMPLETED);
    }

    private List<InterviewStatusType> getValidFindCommentStatusType() {
        if (this == COACH) {
            return List.of(InterviewStatusType.COMPLETE, InterviewStatusType.COACH_COMPLETED);
        }
        return List.of(InterviewStatusType.COMPLETE, InterviewStatusType.CREW_COMPLETED);
    }
}


