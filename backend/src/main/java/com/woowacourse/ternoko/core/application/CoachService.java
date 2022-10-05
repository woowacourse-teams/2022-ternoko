package com.woowacourse.ternoko.core.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.coach.CoachRepository;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.core.dto.request.CalendarRequest;
import com.woowacourse.ternoko.core.dto.request.CoachUpdateRequest;
import com.woowacourse.ternoko.core.dto.response.CoachResponse;
import com.woowacourse.ternoko.core.dto.response.CoachesResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;

    @Transactional(readOnly = true)
    public CoachesResponse findCoaches() {
        final List<Coach> coaches = coachRepository.findAll();

        final List<CoachResponse> coachResponses = coaches.stream()
                .map(coach -> CoachResponse.of(coach, countAvailableDateTimeByCoachId(coach.getId())))
                .collect(Collectors.toList());

        return new CoachesResponse(coachResponses);
    }

    @Transactional(readOnly = true)
    public CoachResponse findCoach(final Long coachId) {

        final Coach coach = getCoachById(coachId);
        return CoachResponse.of(coach, countAvailableDateTimeByCoachId(coachId));
    }

    private Long countAvailableDateTimeByCoachId(Long coachId) {
        final LocalDateTime startCountRangeTime = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(00, 00));
        final LocalDateTime endCountRangeTime = startCountRangeTime.plusMonths(1);

        return availableDateTimeRepository.countByCoachId(coachId,
                startCountRangeTime, endCountRangeTime);

    }

    private Coach getCoachById(final Long coachId) {
        return coachRepository.findById(coachId)
                .orElseThrow(() -> new CoachNotFoundException(COACH_NOT_FOUND, coachId));
    }

    public void putAvailableDateTimesByCoachId(final Long coachId,
                                               final CalendarRequest calendarRequest) {
        final Coach coach = getCoachById(coachId);

        final List<AvailableDateTimeRequest> availableDateTimeRequests = calendarRequest.getCalendarTimes();
        for (AvailableDateTimeRequest availableDateTime : availableDateTimeRequests) {
            putAvailableTime(coach, availableDateTime);
        }
    }

    private void putAvailableTime(final Coach coach, final AvailableDateTimeRequest availableDateTime) {
        availableDateTimeRepository.deleteAllByCoachAndYearAndMonthAndStatus(
                coach.getId(),
                availableDateTime.getYear(),
                availableDateTime.getMonth(),
                OPEN);
        availableDateTimeRepository.saveAll(toAvailableDateTimes(coach, availableDateTime.getTimes()));
    }

    private List<AvailableDateTime> toAvailableDateTimes(final Coach coach,
                                                        final List<AvailableDateTimeSummaryRequest> times) {
        return times.stream()
                .map(time -> new AvailableDateTime(coach.getId(), time.getTime(), time.getAvailableDateTimeStatus()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvailableDateTime> findAvailableDateTimesByCoachId(
            final Long coachId,
            final int year,
            final int month) {

        return availableDateTimeRepository.findOpenAvailableDateTimesByCoachId(coachId, year, month);
    }

    @Transactional(readOnly = true)
    public List<AvailableDateTime> findAvailableDateTimesByCoachIdAndInterviewId(
            final Long interviewId,
            final Long coachId,
            final int year,
            final int month) {

        return availableDateTimeRepository
                .findByCoachIdAndYearAndMonthAndOpenOrInterviewStartTime(interviewId, coachId, year, month);
    }

    //:todo partUpdateCoach 같은데 현재 PR 에서 변경해도 될지?
    public void partUpdateCrew(Long coachId, CoachUpdateRequest coachUpdateRequest) {
        coachRepository.updateNickNameAndImageUrlAndIntroduce(coachId,
                coachUpdateRequest.getNickname(),
                coachUpdateRequest.getImageUrl(),
                coachUpdateRequest.getIntroduce());
    }
}
