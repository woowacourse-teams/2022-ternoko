package com.woowacourse.ternoko.core.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.CREW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DATE;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DUPLICATE_DATE_TIME;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.USED;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.common.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewCrewIdException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewDateException;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
import com.woowacourse.ternoko.core.domain.interview.InterviewStatusType;
import com.woowacourse.ternoko.core.domain.interview.event.InterviewCanceledEvent;
import com.woowacourse.ternoko.core.domain.interview.event.InterviewCreatedEvent;
import com.woowacourse.ternoko.core.domain.interview.event.InterviewDeletedEvent;
import com.woowacourse.ternoko.core.domain.interview.event.InterviewUpdatedEvent;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.coach.CoachRepository;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.domain.member.crew.CrewRepository;
import com.woowacourse.ternoko.core.dto.request.FormItemRequest;
import com.woowacourse.ternoko.core.dto.request.InterviewRequest;
import com.woowacourse.ternoko.core.dto.response.InterviewResponse;
import com.woowacourse.ternoko.core.dto.response.ScheduleResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    public Long create(final Long crewId, final InterviewRequest interviewRequest) {
        validateDuplicateStartTimeByCrew(crewId, interviewRequest.getInterviewDatetime());

        final AvailableDateTime availableDateTime = getAvailableTime(interviewRequest);
        validateOpenTime(availableDateTime);
        validateAfterToday(availableDateTime);
        availableDateTime.changeStatus(USED);

        final Interview interview = convertInterview(crewId, interviewRequest);

        applicationEventPublisher.publishEvent(new InterviewCreatedEvent(this, interview));
        return interviewRepository.save(interview).getId();
    }

    private void validateDuplicateStartTimeByCrew(final Long crewId, final LocalDateTime interviewDateTime) {
        if (interviewRepository.existsByCrewIdAndInterviewStartTime(crewId, interviewDateTime)) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DUPLICATE_DATE_TIME);
        }
    }

    private void validateOpenTime(final AvailableDateTime availableDateTime) {
        if (availableDateTime.isUsed()) {
            throw new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME);
        }

    }

    private Interview convertInterview(final Long crewId, final InterviewRequest interviewRequest) {
        final Crew crew = getCrewById(crewId);
        final Coach coach = getCoachById(interviewRequest.getCoachId());
        final List<FormItem> formItems = convertFormItem(interviewRequest.getInterviewQuestions());

        return Interview.of(interviewRequest.getInterviewDatetime(), coach, crew,
                formItems);
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
                .orElseThrow(() -> new CoachNotFoundException(COACH_NOT_FOUND, interviewRequest));
    }

    private AvailableDateTime getAvailableTime(final InterviewRequest interviewRequest) {
        return availableDateTimeRepository.findByCoachIdAndInterviewDateTime(interviewRequest.getCoachId(),
                        interviewRequest.getInterviewDatetime())
                .orElseThrow(() -> new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME));
    }

    private AvailableDateTime getAvailableTime(final Interview interview) {
        return availableDateTimeRepository.findByCoachIdAndInterviewDateTime(interview.getCoach().getId(),
                        interview.getInterviewStartTime())
                .orElseThrow(() -> new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME));
    }

    @Transactional(readOnly = true)
    public InterviewResponse findInterviewResponseById(final Long interviewId) {
        final Interview interview = getInterviewById(interviewId);
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
        final List<Interview> interviews = getInterviews(coachId, year, month);
        return ScheduleResponse.from(filterCanceledInterview(interviews));
    }

    private List<Interview> getInterviews(final Long coachId, final Integer year, final Integer month) {
        final Integer lastDayOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH).lengthOfMonth();
        final LocalDateTime startOfMonth = LocalDateTime.of(year, month, FIRST_DAY_OF_MONTH, START_HOUR, START_MINUTE);
        final LocalDateTime endOfMonth = LocalDateTime.of(year, month, lastDayOfMonth, END_HOUR, END_MINUTE);

        return interviewRepository
                .findAllByCoachIdAndDateRange(startOfMonth, endOfMonth, coachId);
    }

    private List<Interview> filterCanceledInterview(final List<Interview> interviews) {
        return interviews.stream()
                .filter(interview -> !InterviewStatusType.isCanceled(interview.getInterviewStatusType()))
                .collect(Collectors.toList());
    }

    public void update(final Long crewId,
                       final Long interviewId,
                       final InterviewRequest interviewRequest) {
        validateDuplicateStartTimeByCrew(crewId, interviewRequest.getInterviewDatetime());
        final Interview interview = getInterviewById(interviewId);
        final Interview origin = interview.copyOf();

        final AvailableDateTime availableDateTime = getAvailableTime(interviewRequest);
        validateAfterToday(availableDateTime);
        changeAvailableDateTimeStatus(interview, interviewRequest);

        interview.update(convertInterview(crewId, interviewRequest));
        applicationEventPublisher.publishEvent(new InterviewUpdatedEvent(this, origin, interview));
    }

    private void validateAfterToday(final AvailableDateTime availableDateTime) {
        if (availableDateTime.isPast() || availableDateTime.isToday()) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DATE);
        }
    }

    private void changeAvailableDateTimeStatus(final Interview originalInterview,
                                               final InterviewRequest interviewRequest) {
        changeAvailableTimeStatusIfPresent(originalInterview.getCoach().getId(),
                originalInterview.getInterviewStartTime(),
                OPEN);
        final AvailableDateTime afterTime = getAvailableTime(interviewRequest);
        afterTime.changeStatus(USED);
    }

    private Interview getInterviewById(Long interviewId) {
        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
    }

    public void cancelAndDeleteAvailableTime(final Long coachId, final Long interviewId,
                                             final boolean onlyInterview) {
        final Interview canceledInterview = cancel(coachId, interviewId);

        final AvailableDateTime unavailableTime = getAvailableTime(canceledInterview);
        if (!onlyInterview) {
            availableDateTimeRepository.delete(unavailableTime);
            return;
        }
        unavailableTime.changeStatus(OPEN);
    }

    private Interview cancel(final Long coachId, final Long interviewId) {
        final Interview interview = getInterviewById(interviewId);
        interview.cancel(coachId);
        applicationEventPublisher.publishEvent(new InterviewCanceledEvent(this, interview));
        return interview;
    }

    public void delete(final Long crewId, final Long interviewId) {
        final Interview interview = getInterviewById(interviewId);
        deleteInterview(crewId, interview);

        changeAvailableTimeStatusIfPresent(interview.getCoach().getId(), interview.getInterviewStartTime(), OPEN);
        applicationEventPublisher.publishEvent(new InterviewDeletedEvent(this, interview));
    }

    private void changeAvailableTimeStatusIfPresent(final Long coachId,
                                                    final LocalDateTime startTime,
                                                    final AvailableDateTimeStatus statusType) {
        final Optional<AvailableDateTime> time = availableDateTimeRepository.findByCoachIdAndInterviewDateTime(
                coachId,
                startTime);

        time.ifPresent(it -> it.changeStatus(statusType));
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
}
