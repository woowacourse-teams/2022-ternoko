package com.woowacourse.ternoko.comment.domain;

import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_INTERVIEW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_MEMBER_ID;

import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.comment.exception.InvalidCommentInterviewIdException;
import com.woowacourse.ternoko.comment.exception.InvalidCommentMemberIdException;
import com.woowacourse.ternoko.interview.domain.Interview;
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
@NoArgsConstructor
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

    public Comment(final Long memberId, final Interview interview, final String comment) {
        this.memberId = memberId;
        this.interview = interview;
        this.comment = comment;
    }

    public void update(CommentRequest commentRequest) {
        this.comment = commentRequest.getComment();
    }

    public void validMember(final Long memberId) {
        if (!this.memberId.equals(memberId)) {
            throw new InvalidCommentMemberIdException(INVALID_COMMENT_MEMBER_ID);
        }
    }

    public void validInterview(final Long interviewId) {
        if (!this.interview.getId().equals(interviewId)) {
            throw new InvalidCommentInterviewIdException(INVALID_COMMENT_INTERVIEW_ID);
        }
    }
}
