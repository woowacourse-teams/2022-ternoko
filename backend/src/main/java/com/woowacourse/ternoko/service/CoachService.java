package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.common.exception.type.ExceptionType.COACH_NOT_FOUND;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeRepository;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeRequest;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.dto.CalendarRequest;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachUpdateRequest;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.repository.CoachRepository;
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
                .map(CoachResponse::from)
                .collect(Collectors.toList());

        return new CoachesResponse(coachResponses);
    }

    @Transactional(readOnly = true)
    public CoachResponse findCoach(final Long coachId) {
        final Coach coach = getCoachById(coachId);
        return CoachResponse.from(coach);
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
                .findAvailableDateTimesByCoachIdAndInterviewId(interviewId, coachId, year, month);
    }

    public void partUpdateCrew(Long coachId, CoachUpdateRequest coachUpdateRequest) {
        coachRepository.updateNickNameAndImageUrlAndIntroduce(coachId,
                coachUpdateRequest.getNickname(),
                coachUpdateRequest.getImageUrl(),
                coachUpdateRequest.getIntroduce());
    }
}
