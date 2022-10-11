package com.woowacourse.ternoko.core.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.AVAILABLE_DATE_TIME_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.CREW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_BY_MEMBER;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DUPLICATE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.ExceptionType.USED_BY_OTHER;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.isCanceled;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

import com.woowacourse.ternoko.common.exception.AvailableDateTimeNotFoundException;
import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.common.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewCrewIdException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewDateException;
import com.woowacourse.ternoko.common.exception.exception.CoachInvalidException;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
import com.woowacourse.ternoko.core.domain.interview.InvalidInterviewMemberException;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.coach.CoachRepository;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.domain.member.crew.CrewRepository;
import com.woowacourse.ternoko.core.dto.request.FormItemRequest;
import com.woowacourse.ternoko.core.dto.request.InterviewRequest;
import com.woowacourse.ternoko.core.dto.response.InterviewResponse;
import com.woowacourse.ternoko.core.dto.response.ScheduleResponse;
import com.woowacourse.ternoko.support.alarm.AlarmResponse;
import com.woowacourse.ternoko.support.alarm.AlarmResponseCache;
import com.woowacourse.ternoko.support.alarm.SlackMessageType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
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
    private final InterviewRepository interviewRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;
    private final AlarmResponseCache cache;

    @Transactional(isolation = SERIALIZABLE)
    public Long create(final Long crewId, final InterviewRequest interviewRequest) {
        final Interview interview = convertInterview(crewId, interviewRequest);
        validateDuplicateStartTimeByCrew(interviewRequest.getInterviewDatetime(), crewId);
        validateUsedTime(interview);
        interview.changeAvailableTimeStatus(USED);
        setCreateMessage(interview);
        return interviewRepository.save(interview).getId();
    }

    private void validateDuplicateStartTimeByCrew(final LocalDateTime startTime,
                                                  final Long crewId) {
        final List<Interview> interviews = interviewRepository.findAllByCrewIdAndInterviewStartTime(crewId, startTime);
        final boolean existNotCanceled = interviews.stream()
                .anyMatch(interview -> !interview.isCanceled());
        if (existNotCanceled) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DUPLICATE_DATE_TIME);
        }
    }

    private Interview convertInterview(final Long crewId, final InterviewRequest interviewRequest) {
        final Crew crew = getCrewById(crewId);
        final Coach coach = getCoachById(interviewRequest.getCoachId());
        final List<FormItem> formItems = convertFormItem(interviewRequest.getInterviewQuestions());
        final Long availableDateTimeId = interviewRequest.getAvailableDateTimeId();
        final AvailableDateTime availableDateTime = getAvailableDateTimeById(availableDateTimeId);

        return Interview.create(availableDateTime, coach, crew, formItems);
    }

    private void validateUsedTime(final Interview interview) {
        if (interview.getAvailableDateTime().isUsed()) {
            throw new InvalidInterviewDateException(USED_BY_OTHER);
        }
    }

    private AvailableDateTime getAvailableDateTimeById(final Long availableDateTimeId) {
        return availableDateTimeRepository.findById(availableDateTimeId)
                .orElseThrow(() -> new AvailableDateTimeNotFoundException(AVAILABLE_DATE_TIME_NOT_FOUND,
                        availableDateTimeId));
    }

    private List<FormItem> convertFormItem(final List<FormItemRequest> interviewQuestions) {
        return interviewQuestions.stream()
                .map(FormItemRequest::toFormItem)
                .collect(Collectors.toList());
    }

    private Crew getCrewById(final Long crewId) {
        return crewRepository.findById(crewId)
                .orElseThrow(() -> new CrewNotFoundException(CREW_NOT_FOUND, crewId));
    }

    private Coach getCoachById(final Long interviewRequest) {
        return coachRepository.findById(interviewRequest)
                .orElseThrow(() -> new CoachInvalidException(COACH_NOT_FOUND, interviewRequest));
    }

    @Transactional(readOnly = true)
    public InterviewResponse findInterviewResponseById(final Long memberId, final Long interviewId) {
        final Interview interview = getInterviewById(interviewId);
        validateOwnMember(memberId, interview);
        return InterviewResponse.from(interview);
    }

    private void validateOwnMember(final Long memberId, final Interview interview) {
        if (!interview.containsMember(memberId)) {
            throw new InvalidInterviewMemberException(INVALID_INTERVIEW_BY_MEMBER);
        }
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
        final List<Interview> interviews = getInterviews(coachId, year, month);
        return ScheduleResponse.from(filterAndSortCanceledInterview(interviews));
    }

    private List<Interview> getInterviews(final Long coachId, final Integer year, final Integer month) {
        final int lastDayOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH).lengthOfMonth();
        final LocalDateTime startOfMonth = LocalDateTime.of(year, month, FIRST_DAY_OF_MONTH, START_HOUR, START_MINUTE);
        final LocalDateTime endOfMonth = LocalDateTime.of(year, month, lastDayOfMonth, END_HOUR, END_MINUTE);

        return interviewRepository
                .findAllByCoachIdAndDateRange(startOfMonth, endOfMonth, coachId);
    }

    private List<Interview> filterAndSortCanceledInterview(final List<Interview> interviews) {
        return interviews.stream()
                .filter(interview -> !isCanceled(interview.getInterviewStatusType()))
                .sorted(Comparator.comparing(Interview::getInterviewStartTime))
                .collect(Collectors.toList());
    }

    public void update(final Long crewId,
                       final Long interviewId,
                       final InterviewRequest interviewRequest) {
        final Interview interview = getInterviewById(interviewId);
        validateDuplicate(interviewId, crewId, interviewRequest.getInterviewDatetime());
        validateUsedAvailableDateTime(interview.getAvailableDateTime(), interviewRequest.getAvailableDateTimeId());

        final Interview origin = interview.copyOf();
        final Interview updateInterview = convertInterview(crewId, interviewRequest);

        updateInterview.changeAvailableTimeStatus(USED);
        interview.update(updateInterview);
        setUpdateMessage(interview, origin);
    }

    private void validateDuplicate(final Long interviewId,
                                   final Long crewId,
                                   final LocalDateTime interviewDateTime) {
        if (interviewRepository.existsByNotIdAndCrewIdAndInterviewStartTime(interviewId, crewId, interviewDateTime)) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DUPLICATE_DATE_TIME);
        }
    }

    private void validateUsedAvailableDateTime(final AvailableDateTime origin, final Long updateAvailableDateTimeId) {
        final AvailableDateTime update = getAvailableDateTimeById(updateAvailableDateTimeId);
        if (update.isUsed() && !origin.isSame(update.getId())) {
            throw new InvalidInterviewDateException(USED_BY_OTHER);
        }
    }

    private Interview getInterviewById(Long interviewId) {
        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
    }

    public void cancelAndDeleteAvailableTime(final Long interviewId,
                                             final boolean onlyInterview) {

        final Interview interview = getInterviewById(interviewId);
        if (onlyInterview) {
            saveDuplicatedAvailableDateTimeOfInterview(interview);
            interview.cancel();
            setCancelMessage(interview);
            return;
        }

        interview.cancel();
        setCancelMessage(interview);
    }

    private void saveDuplicatedAvailableDateTimeOfInterview(final Interview interview) {
        final AvailableDateTime availableDateTimeOfCurrentInterview = interview.getAvailableDateTime();
        final AvailableDateTime newAvailableDateTime = new AvailableDateTime(
                availableDateTimeOfCurrentInterview.getCoachId(),
                availableDateTimeOfCurrentInterview.getLocalDateTime(), OPEN);
        availableDateTimeRepository.save(newAvailableDateTime);
    }

    public void delete(final Long crewId, final Long interviewId) {
        final Interview interview = getInterviewById(interviewId);
        openAvailableDateTimeIfNotCanceled(interview);
        deleteInterview(crewId, interview);
        setDeleteMessage(interview);
    }

    private void openAvailableDateTimeIfNotCanceled(final Interview interview) {
        if (!interview.isCanceled()) {
            interview.changeAvailableTimeStatus(OPEN);
        }
    }

    private void deleteInterview(final Long crewId, final Interview interview) {
        validateCreator(crewId, interview);
        interviewRepository.delete(interview);
    }

    private void validateCreator(final Long crewId, final Interview interview) {
        if (!interview.isCreatedBy(crewId)) {
            throw new InvalidInterviewCrewIdException(INVALID_INTERVIEW_CREW_ID);
        }
    }

    @Transactional(readOnly = true)
    public List<AvailableDateTime> findAvailableDateTimesByCoachIdOrInterviewId(
            final Long interviewId,
            final Long coachId,
            final int year,
            final int month) {

        final List<AvailableDateTime> availableDateTimes = availableDateTimeRepository.findOpenAvailableDateTimesByCoachId(
                coachId, year, month);
        final Interview interview = getInterviewById(interviewId);

        if (!interview.isCanceled()) {
            availableDateTimes.add(interview.getAvailableDateTime());
        }

        return availableDateTimes.stream()
                .sorted(Comparator.comparing(AvailableDateTime::getLocalDateTime))
                .collect(Collectors.toList());
    }

    private void setCreateMessage(final Interview interview) {
        cache.setOrigin(AlarmResponse.from(interview));
        cache.setMessageType(SlackMessageType.CREW_CREATE);
    }

    private void setUpdateMessage(final Interview interview, final Interview origin) {
        cache.setOrigin(AlarmResponse.from(origin));
        cache.setUpdate(AlarmResponse.from(interview));
        cache.setMessageType(SlackMessageType.CREW_UPDATE);
    }

    private void setCancelMessage(final Interview interview) {
        cache.setOrigin(AlarmResponse.from(interview));
        cache.setMessageType(SlackMessageType.COACH_CANCEL);
    }

    private void setDeleteMessage(final Interview interview) {
        cache.setOrigin(AlarmResponse.from(interview));
    }
}
