package com.woowacourse.ternoko.interview.application;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.CREW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_DATE;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_DUPLICATE_DATE_TIME;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.repository.AvailableDateTimeRepository;
import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import com.woowacourse.ternoko.interview.domain.InterviewStatusType;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import com.woowacourse.ternoko.interview.dto.AlarmResponse;
import com.woowacourse.ternoko.interview.dto.FormItemRequest;
import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import com.woowacourse.ternoko.interview.dto.InterviewResponse;
import com.woowacourse.ternoko.interview.dto.ScheduleResponse;
import com.woowacourse.ternoko.interview.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCrewIdException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewDateException;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
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
    private final InterviewRepository interviewRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;

    public AlarmResponse create(final Long crewId, final InterviewRequest interviewRequest) {
        validateDuplicateStartTimeByCrew(crewId, interviewRequest.getInterviewDatetime());

        final AvailableDateTime availableDateTime = getAvailableTime(interviewRequest);
        validateOpenTime(availableDateTime);
        validateAfterToday(availableDateTime);
        availableDateTime.changeStatus(USED);

        final Interview interview = convertInterview(crewId, interviewRequest);
        return AlarmResponse.from(interviewRepository.save(interview));
    }

    private void validateDuplicateStartTimeByCrew(final Long crewId, final LocalDateTime interviewDateTime) {
        if (interviewRepository.existsByCrewIdAndInterviewStartTime(crewId, interviewDateTime)) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DUPLICATE_DATE_TIME);
        }
    }

    public void validateOpenTime(final AvailableDateTime availableDateTime) {
        if (availableDateTime.isUsed()) {
            throw new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME);
        }
    }

    private AvailableDateTime getAvailableTime(final InterviewRequest interviewRequest) {
        return availableDateTimeRepository.findByCoachIdAndInterviewDateTime(interviewRequest.getCoachId(),
                        interviewRequest.getInterviewDatetime())
                .orElseThrow(() -> new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME));
    }

    private Interview convertInterview(final Long crewId, final InterviewRequest interviewRequest) {
        final Crew crew = getCrewById(crewId);
        final Coach coach = getCoachById(interviewRequest.getCoachId());
        final List<FormItem> formItems = convertFormItem(interviewRequest.getInterviewQuestions());

        return Interview.from(interviewRequest.getInterviewDatetime(), coach, crew,
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

    public List<AlarmResponse> update(final Long crewId,
                                      final Long interviewId,
                                      final InterviewRequest interviewRequest) {
        final Interview interview = getInterviewById(interviewId);
        final List<AlarmResponse> alarmResponses = new ArrayList<>();
        alarmResponses.add(AlarmResponse.from(interview));

        final AvailableDateTime availableDateTime = getAvailableTime(interviewRequest);
        validateAfterToday(availableDateTime);
        changeAvailableDateTimeStatus(interviewRequest, interview);

        interview.update(convertInterview(crewId, interviewRequest));

        alarmResponses.add(AlarmResponse.from(interview));
        return alarmResponses;
    }

    private void validateAfterToday(final AvailableDateTime availableDateTime) {
        if (availableDateTime.isPast() || availableDateTime.isToday()) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DATE);
        }
    }

    private void changeAvailableDateTimeStatus(final InterviewRequest interviewRequest,
                                               final Interview originalInterview) {
        final AvailableDateTime beforeTime = getAvailableTime(originalInterview);
        final AvailableDateTime afterTime = getAvailableTime(interviewRequest);

        beforeTime.changeStatus(OPEN);
        afterTime.changeStatus(USED);
    }

    private Interview getInterviewById(Long interviewId) {
        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException(INTERVIEW_NOT_FOUND, interviewId));
    }

    public AlarmResponse cancelAndDeleteAvailableTime(final Long coachId, final Long interviewId,
                                                      final boolean onlyInterview) {
        final Interview canceledInterview = cancel(coachId, interviewId);

        final AvailableDateTime unavailableTime = getAvailableTime(canceledInterview);
        if (!onlyInterview) {
            availableDateTimeRepository.delete(unavailableTime);
            return AlarmResponse.from(canceledInterview);
        }
        unavailableTime.changeStatus(OPEN);
        return AlarmResponse.from(canceledInterview);
    }

    private Interview cancel(final Long coachId, final Long interviewId) {
        final Interview interview = getInterviewById(interviewId);
        interview.cancel(coachId);
        return interview;
    }

    public AlarmResponse delete(final Long crewId, final Long interviewId) {
        final Interview interview = getInterviewById(interviewId);
        deleteInterview(crewId, interview);

        final Optional<AvailableDateTime> time = availableDateTimeRepository.findByCoachIdAndInterviewDateTime(
                interview.getCoach().getId(),
                interview.getInterviewStartTime());

        time.ifPresent(it -> it.changeStatus(OPEN));

        return AlarmResponse.from(interview);
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
