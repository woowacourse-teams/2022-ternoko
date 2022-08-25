package com.woowacourse.ternoko.interview.domain;

import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static com.woowacourse.ternoko.domain.member.MemberType.COACH;
import static com.woowacourse.ternoko.domain.member.MemberType.CREW;

import com.woowacourse.ternoko.comment.exception.InvalidStatusCreateCommentException;
import com.woowacourse.ternoko.domain.member.MemberType;

public enum InterviewStatusType {
    EDITABLE,
    FIXED,
    COMMENT,
    COACH_COMPLETED,
    CREW_COMPLETED,
    COMPLETE,
    CANCELED;

    public static boolean isCanceled(final InterviewStatusType type) {
        return CANCELED.equals(type);
    }

    public void validateCreateComment(final MemberType memberType) {
        if (memberType == COACH && (this == FIXED || this == CREW_COMPLETED)) {
            return;
        }
        if (memberType == CREW && (this == FIXED || this == COACH_COMPLETED)) {
            return;
        }
        throw new InvalidStatusCreateCommentException(INVALID_STATUS_CREATE_COMMENT);
    }

    public boolean canCreateCommentStatusBy(final MemberType memberType) {
        if (memberType.isSameType(COACH) && (this == FIXED || this == CREW_COMPLETED)) {
            return true;
        }
        if (memberType.isSameType(CREW) && (this == FIXED || this == COACH_COMPLETED)) {
            return true;
        }
        return false;
    }

    public boolean canFindCommentBy() {
        return this == COMPLETE || this == COACH_COMPLETED || this == CREW_COMPLETED;
    }
}
