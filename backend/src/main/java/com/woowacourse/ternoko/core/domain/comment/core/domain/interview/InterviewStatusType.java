package com.woowacourse.ternoko.core.domain.comment.core.domain.interview;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static com.woowacourse.ternoko.core.domain.member.MemberType.COACH;
import static com.woowacourse.ternoko.core.domain.member.MemberType.CREW;

import com.woowacourse.ternoko.common.exception.exception.InvalidStatusCreateCommentException;
import com.woowacourse.ternoko.core.domain.member.MemberType;

public enum InterviewStatusType {
    EDITABLE,
    FIXED,
    COMMENT,
    COACH_COMPLETED,
    CREW_COMPLETED,
    COMPLETED,
    CANCELED;

    public static boolean isCanceled(final com.woowacourse.ternoko.core.domain.interview.InterviewStatusType type) {
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
        return this == COMPLETED || this == COACH_COMPLETED || this == CREW_COMPLETED;
    }
}
