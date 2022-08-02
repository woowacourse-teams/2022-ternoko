package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachUpdateRequest;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import com.woowacourse.ternoko.repository.AvailableDateTimeRepository;
import com.woowacourse.ternoko.repository.CoachRepository;
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

        final List<CoachResponse> coachResponses = coaches.stream()
                .map(CoachResponse::from)
                .collect(Collectors.toList());

        return new CoachesResponse(coachResponses);
    }

    @Transactional(readOnly = true)
    public CoachResponse findCoach(final Long coachId) {
        final Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new CoachNotFoundException(ExceptionType.COACH_NOT_FOUND, coachId));
        return CoachResponse.from(coach);
    }

    public void putAvailableDateTimesByCoachId(final Long coachId,
                                               final AvailableDateTimesRequest availableDateTimesRequest) {
        final Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new CoachNotFoundException(COACH_NOT_FOUND, coachId));

        final List<AvailableDateTimeRequest> availableDateTimeRequests = availableDateTimesRequest
                .getCalendarTimes();
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

    public List<AvailableDateTime> toAvailableDateTimes(final Coach coach, final List<LocalDateTime> times) {
        return times.stream()
                .map(time -> new AvailableDateTime(coach, time))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvailableDateTime> findAvailableDateTimesByCoachId(
            final Long coachId,
            final int year,
            final int month) {
        return availableDateTimeRepository.findAvailableDateTimesByCoachId(coachId, year, month);
    }

    public void partUpdateCrew(Long coachId, CoachUpdateRequest coachUpdateRequest) {
        coachRepository.updateNickNameAndImageUrlAndIntroduce(coachId, coachUpdateRequest.getNickname(),
                coachUpdateRequest.getImagUrl(), coachUpdateRequest.getIntroduce());
    }
}
