package com.woowacourse.ternoko.core.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;

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
import java.time.LocalDateTime;
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
        final LocalDateTime now = LocalDateTime.now();

        final List<CoachResponse> coachResponses = coaches.stream()
                .map(coach -> CoachResponse.of(coach, availableDateTimeRepository.countByCoachId(coach.getId(),
                        now, now.plusMonths(1))))
                .collect(Collectors.toList());

        return new CoachesResponse(coachResponses);
    }

    @Transactional(readOnly = true)
    public CoachResponse findCoach(final Long coachId) {
        final LocalDateTime now = LocalDateTime.now();

        final Coach coach = getCoachById(coachId);
        return CoachResponse.of(coach, availableDateTimeRepository.countByCoachId(coachId, now, now.plusMonths(1)));
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
        availableDateTimeRepository.deleteAllByCoachAndYearAndMonth(
                coach.getId(),
                availableDateTime.getYear(),
                availableDateTime.getMonth());
        availableDateTimeRepository.saveAll(toAvailableDateTimes(coach, availableDateTime.getTimes()));
    }

    public List<AvailableDateTime> toAvailableDateTimes(final Coach coach,
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

    public void partUpdateCrew(Long coachId, CoachUpdateRequest coachUpdateRequest) {
        coachRepository.updateNickNameAndImageUrlAndIntroduce(coachId,
                coachUpdateRequest.getNickname(),
                coachUpdateRequest.getImageUrl(),
                coachUpdateRequest.getIntroduce());
    }
}
