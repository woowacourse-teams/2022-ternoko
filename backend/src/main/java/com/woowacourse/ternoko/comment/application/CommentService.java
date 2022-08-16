package com.woowacourse.ternoko.comment.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COMMENT_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_INTERVIEW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_FIND_COMMENT;

import com.woowacourse.ternoko.comment.domain.Comment;
import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.comment.dto.CommentResponse;
import com.woowacourse.ternoko.comment.dto.CommentsResponse;
import com.woowacourse.ternoko.comment.exception.CommentNotFoundException;
import com.woowacourse.ternoko.comment.exception.InvalidCommentInterviewIdException;
import com.woowacourse.ternoko.comment.exception.InvalidCommentMemberIdException;
import com.woowacourse.ternoko.comment.exception.InvalidStatusCreateCommentException;
import com.woowacourse.ternoko.comment.exception.InvalidStatusFindCommentException;
import com.woowacourse.ternoko.comment.repository.CommentRepository;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.domain.InterviewStatusType;
import com.woowacourse.ternoko.domain.MemberType;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import com.woowacourse.ternoko.interview.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final CrewRepository crewRepository;
    private final InterviewRepository interviewRepository;
    private final CommentRepository commentRepository;

    public Long create(final Long memberId, final Long interviewId, final CommentRequest commentRequest) {
        final Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
        final Member member = getMember(memberId, interview);
        final MemberType memberType = getMemberType(memberId);
        if (!memberType.getValidCreateCommentStatusType().contains(interview.getInterviewStatusType())) {
            throw new InvalidStatusCreateCommentException(INVALID_STATUS_CREATE_COMMENT);
        }
        Comment savedComment = commentRepository.save(new Comment(member.getId(), interview, commentRequest.getComment()));
        interview.complete(memberType);

        return savedComment.getId();
    }

    public CommentsResponse findComments(Long memberId, Long interviewId) {
        MemberType requestMemberType = getMemberType(memberId);
        final Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
        getMember(memberId, interview);
        InterviewStatusType interviewStatus = interview.getInterviewStatusType();
        if (!requestMemberType.getValidFindCommentStatusType().contains(interviewStatus)) {
            throw new InvalidStatusFindCommentException(INVALID_STATUS_FIND_COMMENT);
        }
        List<Comment> comments = commentRepository.findByInterviewId(interviewId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
//            MemberType memberType = getMemberType(comment.getMember().getId());
            MemberType memberType = getMemberType(comment.getMemberId());
            commentResponses.add(CommentResponse.of(memberType, comment));
        }
        return CommentsResponse.from(commentResponses);
    }

    public void update(Long memberId, Long interviewId, Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND, commentId));

        if (!Objects.equals(comment.getMemberId(), memberId)) {
            throw new InvalidCommentMemberIdException(INVALID_COMMENT_MEMBER_ID);
        }

        if (!comment.getInterview().getId().equals(interviewId)) {
            throw new InvalidCommentInterviewIdException(INVALID_COMMENT_INTERVIEW_ID);
        }
        comment.update(commentRequest);
    }

    private Member getMember(Long memberId, Interview interview) {
        if (interview.getCoach().sameMember(memberId)) {
            return interview.getCoach();
        }
        if (interview.getCrew().sameMember(memberId)) {
            return interview.getCrew();
        }
        throw new MemberNotFoundException(INVALID_INTERVIEW_MEMBER_ID);
    }

    private MemberType getMemberType(final Long memberId) {
        if (crewRepository.findById(memberId).isPresent()) {
            return MemberType.CREW;
        }
        return MemberType.COACH;
    }
}
