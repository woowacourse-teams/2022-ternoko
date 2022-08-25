package com.woowacourse.ternoko.interview.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.CREW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DUPLICATE_DATE_TIME;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeRepository;
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
        availableDateTime.validateUsableAvailableDateTime();

        final Interview interview = convertInterview(crewId, interviewRequest);
        availableDateTime.changeStatus();

        return AlarmResponse.from(interviewRepository.save(interview));
    }

    private void validateDuplicateStartTimeByCrew(final Long crewId, final LocalDateTime interviewDateTime) {
        if (interviewRepository.existsByCrewIdAndInterviewStartTime(crewId, interviewDateTime)) {
            throw new InvalidInterviewDateException(INVALID_INTERVIEW_DUPLICATE_DATE_TIME);
        }
    }

    private Interview convertInterview(final Long crewId, final InterviewRequest interviewRequest) {
        final Crew crew = findCrew(crewId);
        final Coach coach = findCoach(interviewRequest.getCoachId());
        final List<FormItem> formItems = convertFormItem(interviewRequest.getInterviewQuestions());
        final AvailableDateTime availableTime = getAvailableTime(interviewRequest);

        availableTime.validateAvailableDateTime();

        return Interview.from(interviewRequest.getInterviewDatetime(), coach, crew,
                formItems);
    }

    private Crew findCrew(final Long crewId) {
        return crewRepository.findById(crewId)
                .orElseThrow(() -> new CrewNotFoundException(CREW_NOT_FOUND, crewId));
    }

    private Coach findCoach(final Long interviewRequest) {
        return coachRepository.findById(interviewRequest)
                .orElseThrow(() -> new CoachNotFoundException(COACH_NOT_FOUND, interviewRequest));
    }

    private List<FormItem> convertFormItem(final List<FormItemRequest> interviewQuestions) {
        return interviewQuestions.stream()
                .map(FormItemRequest::toFormItem)
                .collect(Collectors.toList());
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
        final List<Interview> interviews = getInterviews(coachId, year, month);
        final List<Interview> excludeCanceledInterviews = filteringCanceledInterview(interviews);
        return ScheduleResponse.from(excludeCanceledInterviews);
    }

    private List<Interview> filteringCanceledInterview(final List<Interview> interviews) {
        return interviews.stream()
                .filter(interview -> !InterviewStatusType.isCanceled(interview.getInterviewStatusType()))
                .collect(Collectors.toList());
    }

    private List<Interview> getInterviews(final Long coachId, final Integer year, final Integer month) {
        final Integer lastDayOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH).lengthOfMonth();
        final LocalDateTime startOfMonth = LocalDateTime.of(year, month, FIRST_DAY_OF_MONTH, START_HOUR, START_MINUTE);
        final LocalDateTime endOfMonth = LocalDateTime.of(year, month, lastDayOfMonth, END_HOUR, END_MINUTE);

        final List<Interview> interviews = interviewRepository
                .findAllByCoachIdAndDateRange(startOfMonth, endOfMonth, coachId);
        return interviews;
    }

    public List<AlarmResponse> update(final Long crewId,
                                      final Long interviewId,
                                      final InterviewRequest interviewRequest) {
        final Interview interview = findInterviewById(interviewId);
        List<AlarmResponse> alarmResponses = new ArrayList<>();
        alarmResponses.add(AlarmResponse.from(interview));

        interview.update(convertInterview(crewId, interviewRequest));
        changeAvailableDateTimeStatus(interviewRequest, interview);

        alarmResponses.add(AlarmResponse.from(interview));
        return alarmResponses;
    }

    private void changeAvailableDateTimeStatus(InterviewRequest interviewRequest, Interview originalInterview) {
        final AvailableDateTime beforeUsedTime = getAvailableTime(originalInterview);
        final AvailableDateTime afterUsedTime = getAvailableTime(interviewRequest);

        beforeUsedTime.changeStatus();
        afterUsedTime.changeStatus();
    }

    private Interview findInterviewById(Long interviewId) {
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
        unavailableTime.changeStatus();
        return AlarmResponse.from(canceledInterview);
    }

    private Interview cancel(final Long coachId, final Long interviewId) {
        final Interview interview = findInterviewById(interviewId);
        interview.cancel(coachId);
        return interview;
    }

    public AlarmResponse delete(final Long crewId, final Long interviewId) {
        final Interview interview = findInterviewById(interviewId);
        deleteInterview(crewId, interview);

        final Optional<AvailableDateTime> time = availableDateTimeRepository.findByCoachIdAndInterviewDateTime(
                interview.getCoach().getId(),
                interview.getInterviewStartTime());

        time.ifPresent(AvailableDateTime::changeStatus);

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
