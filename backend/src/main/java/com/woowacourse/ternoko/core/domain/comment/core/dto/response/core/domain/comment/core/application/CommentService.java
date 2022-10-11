package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COMMENT_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_FIND_COMMENT;

import com.woowacourse.ternoko.common.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.common.exception.exception.CommentNotFoundException;
import com.woowacourse.ternoko.common.exception.exception.InvalidStatusFindCommentException;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.Comment;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.CommentRepository;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.InterviewRepository;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.member.MemberType;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.request.CommentRequest;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.response.CommentsResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

    private final InterviewRepository interviewRepository;
    private final CommentRepository commentRepository;

    public Long create(final Long memberId, final Long interviewId, final com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.request.CommentRequest commentRequest) {
        final com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));

        final MemberType memberType = interview.findMemberType(memberId);
        final com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.Comment comment = commentRepository.save(
                com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.Comment
                        .create(memberId, interview, commentRequest.getComment(), memberType));

        interview.complete(memberType);
        return comment.getId();
    }

    @Transactional(readOnly = true)
    public com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.response.CommentsResponse findComments(final Long memberId, final Long interviewId) {
        final com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
        interview.findMemberType(memberId);
        validateFindStatus(interview);
        final List<com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.Comment> comments = commentRepository.findByInterviewId(interviewId);
        return CommentsResponse.of(comments, interview);
    }

    private void validateFindStatus(final Interview interview) {
        if (!interview.canFindCommentBy()) {
            throw new InvalidStatusFindCommentException(INVALID_STATUS_FIND_COMMENT);
        }
    }

    public void update(final Long memberId,
                       final Long interviewId,
                       final Long commentId,
                       final CommentRequest commentRequest) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND, commentId));
        comment.update(memberId, interviewId, commentRequest.getComment());
    }
}
