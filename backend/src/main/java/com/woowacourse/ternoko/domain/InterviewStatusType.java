package com.woowacourse.ternoko.domain;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_FIND_COMMENT;

import com.woowacourse.ternoko.comment.exception.InvalidStatusCreateCommentException;
import com.woowacourse.ternoko.comment.exception.InvalidStatusFindCommentException;
import com.woowacourse.ternoko.login.domain.MemberType;

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
        if (memberType == MemberType.COACH && (this == COMMENT || this == CREW_COMPLETED)) {
            return;
        }
        if (memberType == MemberType.CREW && (this == COMMENT || this == COACH_COMPLETED)) {
            return;
        }
        throw new InvalidStatusCreateCommentException(INVALID_STATUS_CREATE_COMMENT);
    }

    public void validateFindComment(final MemberType memberType) {
        if (memberType == MemberType.COACH && (this == COMPLETE || this == COACH_COMPLETED)) {
            return;
        }
        if (memberType == MemberType.CREW && (this == COMPLETE || this == CREW_COMPLETED)) {
            return;
        }
        throw new InvalidStatusFindCommentException(INVALID_STATUS_FIND_COMMENT);
    }
}
