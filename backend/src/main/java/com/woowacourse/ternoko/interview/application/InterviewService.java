package com.woowacourse.ternoko.interview.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.CREW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_COACH_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_DUPLICATE_DATE_TIME;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeRepository;
import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.interview.domain.FormItemRepository;
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
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCoachIdException;
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
    private final FormItemRepository formItemRepository;

    public AlarmResponse create(final Long crewId, final InterviewRequest interviewRequest) {
        validateDuplicateStartTimeByCrew(crewId, interviewRequest.getInterviewDatetime());

        final AvailableDateTime availableDateTime = findAvailableTime(interviewRequest);
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
        final AvailableDateTime availableTime = findAvailableTime(interviewRequest);

        availableTime.validateAvailableDateTime();

        return Interview.from(interviewRequest.getInterviewDatetime(), crew, coach,
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

    public List<AlarmResponse> update(final Long crewId,
                                      final Long interviewId,
                                      final InterviewRequest interviewRequest) {
        final Interview interview = findInterviewById(interviewId);
        validateChangeAuthorization(interview, crewId);

        List<AlarmResponse> alarmResponses = new ArrayList<>();
        alarmResponses.add(AlarmResponse.from(interview));

        interview.update(generateUpdateInterview(interview.getCrew(), interviewRequest));
        final AvailableDateTime availableTime = findAvailableTime(interviewRequest);
        availableTime.validateAvailableDateTime();
        changeAvailableDateTimeStatus(interviewRequest, interview);

        alarmResponses.add(AlarmResponse.from(interview));
        return alarmResponses;
    }

    private Interview generateUpdateInterview(final Crew crew, final InterviewRequest interviewRequest) {

        final Long coachId = interviewRequest.getCoachId();
        final Coach coach = findCoach(coachId);

        return new Interview(
                interviewRequest.getInterviewDatetime(),
                interviewRequest.getInterviewDatetime().plusMinutes(30),
                coach,
                crew,
                convertFormItem(interviewRequest.getInterviewQuestions())
        );
    }

    private void changeAvailableDateTimeStatus(InterviewRequest interviewRequest, Interview originalInterview) {
        final AvailableDateTime beforeAvailableDateTime = findAvailableTime(originalInterview.getCoach().getId(),
                originalInterview.getInterviewStartTime());
        final AvailableDateTime afterAvailableDateTime = findAvailableTime(interviewRequest);
        beforeAvailableDateTime.changeStatus();
        afterAvailableDateTime.changeStatus();
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

    public AlarmResponse delete(final Long crewId, final Long interviewId) {
        final Interview interview = findInterviewById(interviewId);
        validateChangeAuthorization(interview, crewId);
        formItemRepository.deleteAll(interview.getFormItems());
        interviewRepository.delete(interview);
        openAvailableTime(interview);
        return AlarmResponse.from(interview);
    }

    public AlarmResponse cancelAndDeleteAvailableTime(final Long coachId, final Long interviewId,
                                                      final boolean onlyInterview) {
        final Interview canceledInterview = cancel(coachId, interviewId);
        final AvailableDateTime unAvailableTime = findAvailableTime(coachId,
                canceledInterview.getInterviewStartTime());
        if (!onlyInterview) {
            availableDateTimeRepository.delete(unAvailableTime);
            return AlarmResponse.from(canceledInterview);
        }
        unAvailableTime.changeStatus();
        return AlarmResponse.from(canceledInterview);
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

    private void openAvailableTime(final Interview interview) {
        Optional<AvailableDateTime> time = availableDateTimeRepository
                .findByCoachIdAndInterviewDateTime(interview.getCoach().getId(),
                        interview.getInterviewStartTime());
        time.ifPresent(it -> it.changeStatus());
    }
}
