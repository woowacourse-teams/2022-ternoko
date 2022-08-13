package com.woowacourse.ternoko.interview.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.COMMENT_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.CREW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_INTERVIEW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_COACH_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DATE;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DUPLICATE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_FIND_COMMENT;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeRepository;
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
import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.domain.InterviewStatusType;
import com.woowacourse.ternoko.domain.MemberType;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.domain.FormItemRepository;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import com.woowacourse.ternoko.interview.dto.FormItemRequest;
import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import com.woowacourse.ternoko.interview.dto.InterviewResponse;
import com.woowacourse.ternoko.interview.dto.ScheduleResponse;
import com.woowacourse.ternoko.interview.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCoachIdException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCrewIdException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewDateException;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class InterviewService {

    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int START_HOUR = 0;
    private static final int START_MINUTE = 0;
    private static final int END_HOUR = 23;
    private static final int END_MINUTE = 59;

    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;
    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;
    private final FormItemRepository formItemRepository;
    private final CommentRepository commentRepository;

    public Interview create(final Long crewId, final InterviewRequest interviewRequest) {
        final Interview interview = convertInterview(crewId, interviewRequest);
        final Interview savedInterview = interviewRepository.save(interview);

        final List<FormItem> formItems = convertFormItem(interviewRequest.getInterviewQuestions());
        for (FormItem formItem : formItems) {
            formItem.addInterview(savedInterview);
        }
        formItemRepository.saveAll(formItems);

        final AvailableDateTime availableDateTime = findAvailableTime(interviewRequest);
        availableDateTime.used();

        return interviewRepository.save(interview);
    }

    private Interview convertInterview(final Long crewId, final InterviewRequest interviewRequest) {
        final LocalDateTime interviewDatetime = interviewRequest.getInterviewDatetime();

        final Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new CrewNotFoundException(CREW_NOT_FOUND, crewId));

        final Coach coach = coachRepository.findById(interviewRequest.getCoachId())
                .orElseThrow(() -> new CoachNotFoundException(COACH_NOT_FOUND, interviewRequest.getCoachId()));

        validateInterviewStartTime(interviewDatetime);
        validateDuplicateStartTime(crewId, interviewRequest);

        return new Interview(
                interviewDatetime,
                interviewDatetime.plusMinutes(30),
                coach,
                crew);
    }

    private void validateDuplicateStartTime(Long crewId, InterviewRequest interviewRequest) {
        if (interviewRepository.existsByCrewIdAndInterviewStartTime(crewId,
                interviewRequest.getInterviewDatetime())) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DUPLICATE_DATE_TIME);
        }
    }

    private void validateInterviewStartTime(final LocalDateTime interviewStartTime) {
        //TODO: 날짜 컨트롤러에서 받아서 검증하는걸로 변경
        final LocalDate nowDay = LocalDate.now();
        final LocalDate interviewDay = interviewStartTime.toLocalDate();

        if (nowDay.isAfter(interviewDay) || nowDay.isEqual(interviewDay)) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DATE);
        }
    }

    private List<FormItem> convertFormItem(final List<FormItemRequest> interviewQuestions) {
        return interviewQuestions.stream()
                .map(FormItemRequest::toFormItem)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InterviewResponse findInterviewResponseById(final Long interviewId) {
        final Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
        return InterviewResponse.from(interview);
    }

    @Transactional(readOnly = true)
    public List<InterviewResponse> findAllByCrewId(final Long crewId) {
        final List<Interview> interviews = interviewRepository.findAllByCrewIdOrderByInterviewStartTime(crewId);

        return interviews.stream()
                .map(InterviewResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ScheduleResponse findAllByCoach(final Long coachId, final Integer year, final Integer month) {
        final Integer lastDayOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH).lengthOfMonth();
        final LocalDateTime startOfMonth = LocalDateTime.of(year, month, FIRST_DAY_OF_MONTH, START_HOUR, START_MINUTE);
        final LocalDateTime endOfMonth = LocalDateTime.of(year, month, lastDayOfMonth, END_HOUR, END_MINUTE);

        final List<Interview> interviews = interviewRepository
                .findAllByCoachIdAndDateRange(startOfMonth, endOfMonth, coachId);

        final List<Interview> excludeCanceledInterviews = interviews.stream()
                .filter(interview -> !InterviewStatusType.isCanceled(interview.getInterviewStatusType()))
                .collect(Collectors.toList());

        return ScheduleResponse.from(excludeCanceledInterviews);
    }

    public Interview update(final Long crewId,
                            final Long interviewId,
                            final InterviewRequest interviewRequest) {
        final Interview originalInterview = findInterviewById(interviewId);

        validateChangeAuthorization(originalInterview, crewId);

        Interview updateInterviewRequest = convertInterview(crewId, interviewRequest);
        List<FormItem> updateInterviewFormItemsRequest = convertFormItem(interviewRequest.getInterviewQuestions());

        updateFromItem(originalInterview, updateInterviewRequest, updateInterviewFormItemsRequest);
        changeAvailableDateTimeStatus(interviewRequest, originalInterview);

        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
    }

    private void changeAvailableDateTimeStatus(InterviewRequest interviewRequest, Interview originalInterview) {
        final AvailableDateTime beforeAvailableDateTime = findAvailableTime(originalInterview.getCoach().getId(),
                originalInterview.getInterviewStartTime());
        final AvailableDateTime afterAvailableDateTime = findAvailableTime(interviewRequest);
        beforeAvailableDateTime.open();
        afterAvailableDateTime.used();
    }

    private void updateFromItem(Interview originalInterview, Interview updateInterviewRequest,
                                List<FormItem> updateInterviewFormItemsRequest) {
        List<FormItem> originalInterviewFormItems = originalInterview.getFormItems();

        for (int i = 0; i < originalInterviewFormItems.size(); i++) {
            originalInterviewFormItems.get(i).update(updateInterviewFormItemsRequest.get(i), originalInterview);
        }
        originalInterview.update(updateInterviewRequest);
    }

    private void validateChangeAuthorization(Interview originalInterview, Long crewId) {
        if (!originalInterview.sameCrew(crewId)) {
            throw new InvalidInterviewCrewIdException(INVALID_INTERVIEW_CREW_ID);
        }
    }

    private Interview findInterviewById(Long interviewId) {
        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
    }

    public Interview delete(final Long crewId, final Long interviewId) {
        final Interview interview = findInterviewById(interviewId);
        validateChangeAuthorization(interview, crewId);
        formItemRepository.deleteAll(interview.getFormItems());
        interviewRepository.delete(interview);
        openAvailableTime(interview);
        return interview;
    }

    public Interview cancelAndDeleteAvailableTime(final Long coachId, final Long interviewId,
                                                  final boolean onlyInterview) {
        final Interview canceledInterview = cancel(coachId, interviewId);
        final AvailableDateTime unAvailableTime = findAvailableTime(coachId,
                canceledInterview.getInterviewStartTime());
        if (!onlyInterview) {
            availableDateTimeRepository.delete(unAvailableTime);
            return canceledInterview;
        }

        unAvailableTime.open();
        return canceledInterview;
    }

    private Interview cancel(final Long coachId, final Long interviewId) {
        final Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
        if (!interview.sameCoach(coachId)) {
            throw new InvalidInterviewCoachIdException(INVALID_INTERVIEW_COACH_ID);
        }
        interview.cancel();
        return interview;
    }

    private AvailableDateTime findAvailableTime(final InterviewRequest interviewRequest) {
        return availableDateTimeRepository.findByCoachIdAndInterviewDateTime(interviewRequest.getCoachId(),
                        interviewRequest.getInterviewDatetime())
                .orElseThrow(() -> new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME));
    }

    private AvailableDateTime findAvailableTime(final Long coachId, final LocalDateTime interviewDateTime) {
        return availableDateTimeRepository.findByCoachIdAndInterviewDateTime(coachId,
                        interviewDateTime)
                .orElseThrow(() -> new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME));
    }

    private void openAvailableTime(final Interview interview) {
        Optional<AvailableDateTime> time = availableDateTimeRepository
                .findByCoachIdAndInterviewDateTime(interview.getCoach().getId(),
                        interview.getInterviewStartTime());
        time.ifPresent(AvailableDateTime::open);
    }

    public Long createComment(final Long memberId, final Long interviewId, final CommentRequest commentRequest) {
        final Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
        final Member member = getMember(memberId, interview);
        final MemberType memberType = getMemberType(memberId);
        if (!memberType.getValidCreateCommentStatusType().contains(interview.getInterviewStatusType())) {
            throw new InvalidStatusCreateCommentException(INVALID_STATUS_CREATE_COMMENT);
        }
        Comment savedComment = commentRepository.save(new Comment(member, interview, commentRequest.getComment()));
        interview.complete(memberType);

        return savedComment.getId();
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
            MemberType memberType = getMemberType(comment.getMember().getId());
            commentResponses.add(CommentResponse.of(memberType, comment));
        }
        return CommentsResponse.from(commentResponses);
    }

    public void updateComment(Long memberId, Long interviewId, Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND, commentId));
        MemberType requestMemberType = getMemberType(memberId);
        if (!comment.getMember().sameMember(memberId)) {
            throw new InvalidCommentMemberIdException(INVALID_COMMENT_MEMBER_ID);
        }
        if (!comment.getInterview().getId().equals(interviewId)) {
            throw new InvalidCommentInterviewIdException(INVALID_COMMENT_INTERVIEW_ID);
        }
        comment.update(commentRequest);
    }
}
