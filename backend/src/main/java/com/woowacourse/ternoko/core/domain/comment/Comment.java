package com.woowacourse.ternoko.core.domain.comment;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_INTERVIEW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.ternoko.common.exception.exception.InvalidCommentInterviewIdException;
import com.woowacourse.ternoko.common.exception.exception.InvalidCommentMemberIdException;
import com.woowacourse.ternoko.common.exception.exception.InvalidStatusCreateCommentException;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "interview_id"})})
@NoArgsConstructor(access = PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private String comment;

    private Comment(final Long memberId, final Interview interview, final String comment) {
        this.memberId = memberId;
        this.interview = interview;
        this.comment = comment;
    }

    public static Comment create(final Long memberId,
                                 final Interview interview,
                                 final String comment,
                                 final MemberType memberType) {
        validateCreateTime(interview);
        validateCreateStatus(interview, memberType);
        return new Comment(memberId, interview, comment);
    }

    private static void validateCreateTime(final Interview interview) {
        if (LocalDateTime.now().isBefore(interview.getInterviewEndTime())) {
            throw new InvalidStatusCreateCommentException(INVALID_STATUS_CREATE_COMMENT);
        }
    }

    private static void validateCreateStatus(final Interview interview, final MemberType memberType) {
        if (!interview.canCreateCommentBy(memberType)) {
            throw new InvalidStatusCreateCommentException(INVALID_STATUS_CREATE_COMMENT);
        }
    }

    public void update(final Long memberId, final Long interviewId, final String comment) {
        validateUpdate(memberId, interviewId);
        this.comment = comment;
    }

    private void validateUpdate(final Long memberId, final Long interviewId) {
        if (!this.isCreatedByMember(memberId)) {
            throw new InvalidCommentMemberIdException(INVALID_COMMENT_MEMBER_ID);
        }
        if (!this.isCreatedByInterview(interviewId)) {
            throw new InvalidCommentInterviewIdException(INVALID_COMMENT_INTERVIEW_ID);
        }
    }

    private boolean isCreatedByMember(final Long memberId) {
        return this.memberId.equals(memberId);
    }

    private boolean isCreatedByInterview(final Long interviewId) {
        return interview.isSame(interviewId);
    }
}
