package com.woowacourse.ternoko.comment.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COMMENT_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;

import com.woowacourse.ternoko.comment.domain.Comment;
import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.comment.dto.CommentResponse;
import com.woowacourse.ternoko.comment.dto.CommentsResponse;
import com.woowacourse.ternoko.comment.exception.CommentNotFoundException;
import com.woowacourse.ternoko.comment.repository.CommentRepository;
import com.woowacourse.ternoko.domain.MemberType;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import com.woowacourse.ternoko.interview.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.repository.CrewRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

    private final CrewRepository crewRepository;
    private final InterviewRepository interviewRepository;
    private final CommentRepository commentRepository;

    public Long create(final Long memberId, final Long interviewId, final CommentRequest commentRequest) {
        final Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));

        final Member member = interview.validateMember(memberId);
        final MemberType memberType = getMemberType(memberId);
        interview.getInterviewStatusType().validateCreateComment(getMemberType(memberId));

        final Comment comment = commentRepository.save(new Comment(memberId, interview, commentRequest.getComment()));

        interview.complete(memberType);

        return comment.getId();
    }

    public CommentsResponse findComments(final Long memberId, final Long interviewId) {
        final Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));

        interview.validateMember(memberId);
        interview.getInterviewStatusType().validateFindComment(getMemberType(memberId));
        List<Comment> comments = commentRepository.findByInterviewId(interviewId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(CommentResponse.of(getMemberType(comment.getMemberId()), comment));
        }
        return CommentsResponse.from(commentResponses);
    }

    public void update(final Long memberId,
                       final Long interviewId,
                       final Long commentId,
                       final CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND, commentId));
        comment.validMember(memberId);
        comment.validInterview(interviewId);
        comment.update(commentRequest);
    }

    private MemberType getMemberType(final Long memberId) {
        if (crewRepository.findById(memberId).isPresent()) {
            return MemberType.CREW;
        }
        return MemberType.COACH;
    }
}
